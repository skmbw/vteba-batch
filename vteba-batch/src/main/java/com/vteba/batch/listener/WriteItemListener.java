package com.vteba.batch.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.annotation.OnWriteError;

/**
 * 写入item时的监听器.可以使用注解，也可以实现对应的监听器。
 * 
 * @author yinlei
 * @date 2016年3月12日 上午11:18:12
 */
public class WriteItemListener<S> implements ItemWriteListener<S> {

	public static final Logger LOGGER = LoggerFactory.getLogger(WriteItemListener.class);
	
	@Override
	@BeforeWrite
	public void beforeWrite(List<? extends S> items) {
		
	}

	@Override
	@AfterWrite
	public void afterWrite(List<? extends S> items) {
		
	}

	@Override
	@OnWriteError
	public void onWriteError(Exception exception, List<? extends S> items) {
		
	}
}
