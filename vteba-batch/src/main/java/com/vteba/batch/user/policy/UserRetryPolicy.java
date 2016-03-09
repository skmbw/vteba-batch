package com.vteba.batch.user.policy;

import javax.inject.Named;

import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;

@Named
public class UserRetryPolicy implements RetryPolicy {

	@Override
	public boolean canRetry(RetryContext context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RetryContext open(RetryContext parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close(RetryContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerThrowable(RetryContext context, Throwable throwable) {
		// TODO Auto-generated method stub
		
	}

}
