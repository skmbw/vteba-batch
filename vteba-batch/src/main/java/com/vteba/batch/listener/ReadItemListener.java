package com.vteba.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.core.annotation.BeforeRead;
import org.springframework.batch.core.annotation.OnReadError;

/**
 * 读取item时的监听器，使用注解了，也可以实现对应的Listener
 * 
 * @author yinlei
 * @date 2016年3月12日 上午11:18:12
 */
public class ReadItemListener<T> implements ItemReadListener<T> {

	public static final Logger LOGGER = LoggerFactory.getLogger(ReadItemListener.class);
	
	@BeforeRead
	public void beforeRead() {
		
	}
	
	@AfterRead
	public void afterRead(T item) {
		
	}

	@OnReadError
	public void onReadError(Exception ex) {
		
	}
}
