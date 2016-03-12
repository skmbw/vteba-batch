package com.vteba.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.annotation.AfterProcess;
import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.core.annotation.OnProcessError;

/**
 * 步骤step处理器监听器。可以使用注解，也可以实现对应的监听器
 * 
 * @author yinlei
 * @date 2016年3月12日 上午11:08:09
 */
public class ProcessExecutionListener<T, S> implements ItemProcessListener<T, S> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessExecutionListener.class);
	
	@BeforeProcess
	public void beforeProcess(T item) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("ProcessExecutionListener start");
		}
	}
	
	@AfterProcess
	public void afterProcess(T item, S result) {
		
	}

	@Override
	@OnProcessError
	public void onProcessError(T item, Exception e) {
		
	}
}
