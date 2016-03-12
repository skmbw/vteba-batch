package com.vteba.batch.listener;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;

/**
 * Job的Step执行监听器.可以使用注解，也可以实现对应的接口。
 * 
 * @author yinlei
 * @date 2016年3月12日 上午11:04:04
 */
@Named
public class StepExecutionListener implements org.springframework.batch.core.StepExecutionListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(StepExecutionListener.class);
	
	@BeforeStep
	public void beforeStep(StepExecution execution) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("before to execute step=[{}].", execution.getId());
		}
	}
	
	@AfterStep
	public ExitStatus afterStep(StepExecution execution) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("after execute step=[{}].", execution.getId());
		}
		return ExitStatus.COMPLETED;
	}
}
