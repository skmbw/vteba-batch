package com.vteba.batch.user.listener;

import javax.inject.Named;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;

@Named
public class UserRetryListener implements RetryListener {

	@Override
	public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback,
			Throwable throwable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback,
			Throwable throwable) {
		// TODO Auto-generated method stub
		
	}

}
