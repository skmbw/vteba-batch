package com.vteba.batch.listener;

import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;

/**
 * 处理item时，跳过处理监听器。可以使用注解或者实现接口。
 * @param <T>
 * @param <S>
 * @author yinlei
 * @date 2016年3月12日 上午11:56:25
 */
public class SkipItemListener<T, S> implements SkipListener<T, S> {

	@Override
	@OnSkipInRead
	public void onSkipInRead(Throwable t) {
		
	}

	@Override
	@OnSkipInWrite
	public void onSkipInWrite(S item, Throwable t) {
		
	}

	@Override
	@OnSkipInProcess
	public void onSkipInProcess(T item, Throwable t) {
		
	}

}
