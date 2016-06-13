package com.vteba.batch.launcher;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.util.Assert;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * 简单实现 {@link JobLauncher} 这个接口. 接口
 * {@link TaskExecutor} 是用来启动一个 {@link Job}. 这意味着TaskExecutor执行器的类型很重要. 如果
 * {@link SyncTaskExecutor} 被使用了，Job任务将在和启动任务发射器JobLauncher相同的线程上执行。  用户应该
 * 理解自己所使用的TaskExecutor是同步的还是异步的. 默认设置的TaskExecutor是同步的.
 *
 * 这个类仅有一个依赖, 一个
 * {@link JobRepository}实现. JobRepository被用来获取一个合法的
 * JobExecution. Repository是必须的，因为提供的{@link Job}
 * 可能会重启一个已经存在的 {@link JobInstance}, 并且仅有这个
 * Repository能可靠的重建它.
 *
 * @author Lucas Ward
 * @author Dave Syer
 * @author Will Schipp
 * @author Michael Minella
 * @author Yinlei
 *
 * @since 1.0
 *
 * @see JobRepository
 * @see TaskExecutor
 */
public class DefaultJobLauncher implements JobLauncher, InitializingBean {

	private static final Logger LOGGER = LogManager.getLogger(DefaultJobLauncher.class);

	private JobRepository jobRepository;

	private AsyncListenableTaskExecutor taskExecutor;
	
	/** 用来保存系统中正在执行的任务，key是jobName，value是线程id */
	private volatile ConcurrentMap<String, String> concurrentMap = new ConcurrentHashMap<>();

	/**
	 * Run the provided job with the given {@link JobParameters}. The
	 * {@link JobParameters} will be used to determine if this is an execution
	 * of an existing job instance, or if a new one should be created.
	 *
	 * @param job the job to be run.
	 * @param jobParameters the {@link JobParameters} for this particular
	 * execution.
	 * @return JobExecutionAlreadyRunningException if the JobInstance already
	 * exists and has an execution already running.
	 * @throws JobRestartException if the execution would be a re-start, but a
	 * re-start is either not allowed or not needed.
	 * @throws JobInstanceAlreadyCompleteException if this instance has already
	 * completed successfully
	 * @throws JobParametersInvalidException
	 */
	@Override
	public JobExecution run(final Job job, final JobParameters jobParameters)
			throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException,
			JobParametersInvalidException {

		Assert.notNull(job, "The Job must not be null.");
		Assert.notNull(jobParameters, "The JobParameters must not be null.");

		final JobExecution jobExecution;
		JobExecution lastExecution = jobRepository.getLastJobExecution(job.getName(), jobParameters);
		if (lastExecution != null) {
			if (!job.isRestartable()) {
				throw new JobRestartException("JobInstance already exists and is not restartable");
			}
			/*
			 * validate here if it has stepExecutions that are UNKNOWN
			 * retrieve the previous execution and check
			 */
			for (StepExecution execution : lastExecution.getStepExecutions()) {
				if (execution.getStatus() == BatchStatus.UNKNOWN) {
					//throw
					throw new JobRestartException("Step [" + execution.getStepName() + "] is of status UNKNOWN");
				}//end if
			}//end for
		}

		// Check the validity of the parameters before doing creating anything
		// in the repository...
		job.getJobParametersValidator().validate(jobParameters);

		/*
		 * There is a very small probability that a non-restartable job can be
		 * restarted, but only if another process or thread manages to launch
		 * <i>and</i> fail a job execution for this instance between the last
		 * assertion and the next method returning successfully.
		 */
		jobExecution = jobRepository.createJobExecution(job.getName(), jobParameters);

		try {
			Callable<Boolean> callable = new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {
					try {
						LOGGER.info("Job: [" + job + "] launched with the following parameters: [" + jobParameters
								+ "]");
						job.execute(jobExecution);
						LOGGER.info("Job: [" + job + "] completed with the following parameters: [" + jobParameters
								+ "] and the following status: [" + jobExecution.getStatus() + "]");
						return true;
					} catch (Throwable t) {
						LOGGER.info("Job: [" + job
								+ "] failed unexpectedly and fatally with the following parameters: [" + jobParameters
								+ "]", t);
						rethrow(t);
					}
					return false;
				}
				
				private void rethrow(Throwable t) {
					if (t instanceof RuntimeException) {
						throw (RuntimeException) t;
					} else if (t instanceof Error) {
						throw (Error) t;
					}
					throw new IllegalStateException(t);
				}
			};
			ListenableFuture<Boolean> future = taskExecutor.submitListenable(callable);
			
		} catch (TaskRejectedException e) {
			jobExecution.upgradeStatus(BatchStatus.FAILED);
			if (jobExecution.getExitStatus().equals(ExitStatus.UNKNOWN)) {
				jobExecution.setExitStatus(ExitStatus.FAILED.addExitDescription(e));
			}
			jobRepository.update(jobExecution);
		}

		return jobExecution;
	}

	/**
	 * Set the JobRepsitory.
	 *
	 * @param jobRepository
	 */
	public void setJobRepository(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	/**
	 * Set the TaskExecutor. (Optional)
	 *
	 * @param taskExecutor
	 */
	public void setTaskExecutor(AsyncListenableTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	/**
	 * Ensure the required dependencies of a {@link JobRepository} have been
	 * set.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.state(jobRepository != null, "A JobRepository has not been set.");
		if (taskExecutor == null) {
			LOGGER.info("No TaskExecutor has been set, defaulting to synchronous executor.");
			// taskExecutor = new SyncTaskExecutor();
			throw new IllegalArgumentException("No TaskExecutor has been set.");
		}
	}
	
	/**
	 * 查询任务是否在运行
	 * @param jobName job名字，Spring bean的名字
	 * @return true运行，false否
	 */
	public boolean isRunning(String jobName) {
		String thread = concurrentMap.get(jobName);
		if (StringUtils.isNotBlank(thread)) {
			return true;
		}
		return false;
	}

	public ConcurrentMap<String, String> getConcurrentMap() {
		return concurrentMap;
	}
	
}

