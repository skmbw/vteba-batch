package com.vteba.batch.user.retry;

import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.BackOffContext;
import org.springframework.retry.backoff.BackOffInterruptedException;
import org.springframework.retry.backoff.BackOffPolicy;

/**
 * 业务补偿操作。每次重试都会触发该接口的backOff方法的回调.
 * 在异常被抛出之前执行
 * 
 * @author yinlei
 * @date 2016年3月9日 上午10:47:28
 */
public class DefaultBackOffPolicy implements BackOffPolicy {

	// 重试时 仅仅执行1次
	@Override
	public BackOffContext start(RetryContext context) {
		BackOffContext backOffContext = new BackOffContextImpl(context);
		return backOffContext;
	}

	// 重试后每次都会执行
	@Override
	public void backOff(BackOffContext backOffContext) throws BackOffInterruptedException {
		// TODO Auto-generated method stub
		
	}

}
