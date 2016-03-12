package com.vteba.batch.listener;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

/**
 * Job执行前后监听器.可以使用注解，也可以实现对应的接口JobExecutionListener。
 * 
 * @author yinlei
 * @date 2016年3月12日 上午11:02:48
 */
@Named
public class JobExecutionListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(JobExecutionListener.class);
	
	@BeforeJob
	public void beforeJob(JobExecution execution) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("start to execute Job=[{}].", execution.getJobId());
		}
	}
	
	@AfterJob
	public void afterJob(JobExecution execution) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("end to execute Job=[{}].", execution.getJobId());
		}
	}
}
