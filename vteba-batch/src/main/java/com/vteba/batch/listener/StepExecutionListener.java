package com.vteba.batch.listener;

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
public class StepExecutionListener implements org.springframework.batch.core.StepExecutionListener {
	
	@BeforeStep
	public void beforeStep(StepExecution execution) {
		
	}
	
	@AfterStep
	public ExitStatus afterStep(StepExecution execution) {
		
		return ExitStatus.COMPLETED;
	}
}
