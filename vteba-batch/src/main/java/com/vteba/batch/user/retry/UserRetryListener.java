package com.vteba.batch.user.retry;

import javax.inject.Named;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;

@Named
//@StepScope
public class UserRetryListener implements RetryListener {

	// spring batch使用RetryTemplate实现，无论是否需发生重试异常，都会执行一次
	// 返回false，抛出TerminatedRetryException(RuntimeException子类)，会事务回滚
	@Override
	public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
		// TODO Auto-generated method stub
		return true;
	}

	// 一定会执行
	@Override
	public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback,
			Throwable throwable) {
		// TODO Auto-generated method stub
		
	}

	// 重试发生错误时触发
	@Override
	public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback,
			Throwable throwable) {
		// TODO Auto-generated method stub
		
	}

}
