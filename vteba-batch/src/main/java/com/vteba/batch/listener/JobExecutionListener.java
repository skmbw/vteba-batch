package com.vteba.batch.listener;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.item.ExecutionContext;

import com.google.common.collect.Maps;

/**
 * Job执行前后监听器.可以使用注解，也可以实现对应的接口JobExecutionListener。
 * 
 * @author yinlei
 * @date 2016年3月12日 上午11:02:48
 */
@Named
public class JobExecutionListener extends AbstractExecutionListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(JobExecutionListener.class);
	
	private static final String TOPIC = "topic";
	
	private static final String TAG = "tag";
	
	@BeforeJob
	public void beforeJob(JobExecution execution) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("start to execute Job jobId=[{}].", execution.getJobId());
		}
	}
	
	@AfterJob
	public void afterJob(JobExecution execution) {
		// job id
		long jobId = execution.getJobId();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("end to execute Job jobId=[{}].", jobId);
		}
		// job execution id// batch_job_execution.job_execution_id
		long jobExecutionId = execution.getId();
		// 获取job执行上下文// batch_job_execution_context
		ExecutionContext executionContext = execution.getExecutionContext();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Job ExecutionContext=[{}], jobId=[{}], jobExecutionId=[{}].", executionContext, jobId, jobExecutionId);
		}
		
		// 获取job实例// batch_job_instance
		JobInstance jobInstance = execution.getJobInstance();
		String jobName = jobInstance.getJobName(); // job名字
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Job Instance=[{}], jobId=[{}].", jobInstance, jobId);
		}
		// job参数 // batch_job_execution_params
		JobParameters jobParameters = execution.getJobParameters();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Job Parameters=[{}], jobId=[{}].", jobParameters, jobId);
		}
		// job的执行状态
		BatchStatus batchStatus = execution.getStatus();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Job Status=[{}], jobId=[{}].", batchStatus, jobId);
		}
		// 还有其他的一些信息，可以根据需要获取
		
		// 这个异常，包括job下step执行的异常，其实step的异常可以单独获取 execution.getStepExecutions();
		List<Throwable> allExceptionList = execution.getAllFailureExceptions();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Job allExceptionList=[{}], jobId=[{}].", allExceptionList, jobId);
		}
		// 可以获取job下的Step，然后进行相关的处理
		Collection<StepExecution> stepExecutions = execution.getStepExecutions();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Job stepExecutions=[{}], jobId=[{}].", stepExecutions, jobId);
		}
		// 这个异常是不包括step执行的异常的
		List<Throwable> exceptionList = execution.getFailureExceptions();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Job exceptionList=[{}], jobId=[{}].", exceptionList, jobId);
		}
		// 不会为null
		if (exceptionList.size() > 0) { // 存在异常，判断有哪些需要处理
		    // 这些异常肯定要记录到日志
		    for (Throwable e : exceptionList) {
		        LOGGER.error("jobId=[{}], executionId=[{}]", jobId, jobExecutionId, e);
		    }
		    // 同时将异常信息发送到邮件或者短信
            Map<String, Object> message = Maps.newHashMap(); // 可以使用其他对象 或者Message，这里举例
            message.put("jobId", jobId);
            message.put("jobExecutionId", jobExecutionId);
            message.put("jobName", jobName);
            message.put("status", batchStatus.name());
            
            try {
                rocketMQMessageProducer.send(TOPIC, TAG, message);
            } catch (Exception e) {
                LOGGER.error("job id=[{}],name=[{}]执行结束,发送MQ出现异常.", 
                    jobId, execution.getJobInstance().getJobName(), e);
            }
		} else { // 没有异常，发送成功的消息到MQ，成功了也可以不管。
		    
		}
	}
}
