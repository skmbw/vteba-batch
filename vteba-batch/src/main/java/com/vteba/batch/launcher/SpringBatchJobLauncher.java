package com.vteba.batch.launcher;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;

import com.vteba.utils.date.DateUtils;

/**
 * Job实例委托类，将调用JobLauncher执行Job。
 * 
 * @author yinlei
 * @date 2016年2月10日 上午11:54:17
 */
public class SpringBatchJobLauncher {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringBatchJobLauncher.class);
	
	@Inject
	private DefaultJobLauncher jobLauncher;
//	@Inject
//	private JobExplorer jobExplorer;
//	@Inject
//	private JobRepository jobRepository;
	
	private String dateFormat;
	private Map<String, String> jobParameters;
	private Job job;

	public void execute() throws Exception {
		String jobName = job.getName();
		if (jobParameters == null) {
			jobParameters = new HashMap<String, String>();
		}
		// 不能用这个参数，参数和job name是唯一确定Job的
		jobParameters.put("executeDatetime", DateUtils.toDateString(dateFormat) + 1);
		JobParameters allParams = translateParams(job, jobParameters);
		
//		boolean jobExist = jobRepository.isJobInstanceExists(jobName, allParams);
//		if (jobExist) {
//			JobExecution jobExecution = jobRepository.getLastJobExecution(jobName, allParams);
//			BatchStatus batchStatus = jobExecution.getStatus();
//			Date endTime = jobExecution.getEndTime();
//			if (endTime == null || batchStatus != BatchStatus.COMPLETED) {
//				
//			}
//		}
		
//		Set<JobExecution> jobExecutionSets = jobExplorer.findRunningJobExecutions(jobName);
//		int size = jobExecutionSets.size();
//		if (size > 1) {
//			if (LOGGER.isInfoEnabled()) {
//				LOGGER.info("已经有任务在运行{},size=[{}].", jobExecutionSets, size);
//			}
//			return;
//		} else {
//			if (LOGGER.isInfoEnabled()) {
//				LOGGER.info("开始执行：JobName=[{}]", jobName);
//			}
//		}
		
		boolean running = jobLauncher.isRunning(jobName);
		
		if (running) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("任务jobName=[{}]正在运行，线程id=[{}]，如果线程id=[null]，说明数据被回调清除，是正常的.", jobName, jobLauncher.getRunningThreadId(jobName));
			}
			return;
		}
		
		JobExecution je = jobLauncher.run(job, allParams);
		Long jobExecutionId = je.getId();
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("开始执行：jobName=[{}], JobExecutionId=[{}]", jobName, jobExecutionId);
		}
	}

	private JobParameters translateParams(Job job, Map<String, String> params) {
		JobParametersBuilder builder = new JobParametersBuilder();

		if (params != null) {
			for (Map.Entry<String, String> param : params.entrySet()) {
				builder.addString(param.getKey(), param.getValue());
			}
		}

		return builder.toJobParameters();
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public void setJobParameters(Map<String, String> jobParameters) {
		this.jobParameters = jobParameters;
	}

	public void setJobLauncher(DefaultJobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
}
