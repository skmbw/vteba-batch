package com.vteba.batch.investor.retry;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;

@Named
public class InvestorRetryListener  implements RetryListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(InvestorRetryListener.class);
	
	// spring batch使用RetryTemplate实现，无论是否需发生重试异常，都会执行一次
	// 返回false，抛出TerminatedRetryException(RuntimeException子类)，会事务回滚.将会中断重试
	@Override
	public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("投资人重试监听器open, context=[{}], callback=[{}].", context, callback);
		}
//		if (context.getLastThrowable() == null) {
//			return false;
//		}
		return true;
	}

	// 一定会执行
	@Override
	public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback,
			Throwable throwable) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("投资人重试监听器close, context=[{}], throw=[{}], callback=[{}].",
					context, throwable, callback);
		}
		
	}

	// 重试发生错误时触发
	@Override
	public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback,
			Throwable throwable) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("投资人重试监听器onError, context=[{}],callback=[{}],throw=[{}].", context, callback, throwable);
		}
	}

}
