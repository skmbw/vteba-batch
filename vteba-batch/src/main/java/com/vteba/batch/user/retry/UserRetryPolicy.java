package com.vteba.batch.user.retry;

import javax.inject.Named;

import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;

/**
 * 重试策略，当发生重试异常时回调
 * 
 * @author yinlei
 * @date 2016年3月9日 上午10:53:27
 */
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
