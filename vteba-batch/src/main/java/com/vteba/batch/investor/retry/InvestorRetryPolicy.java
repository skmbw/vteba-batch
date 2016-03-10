package com.vteba.batch.investor.retry;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;

/**
 * 重试策略，当发生重试异常时回调
 * 
 * @author yinlei
 * @date 2016年3月9日 上午10:53:27
 */
@Named
public class InvestorRetryPolicy implements RetryPolicy {
	private static final Logger LOGGER = LoggerFactory.getLogger(InvestorRetryPolicy.class);
	
	@Override
	public boolean canRetry(RetryContext context) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("重试策略canRetry, context=[{}].", context);
		}
		
		if (context.getRetryCount() <= 2) {
			context.setAttribute("retryCount", 1);
			return true;
		}
		return false;
	}

	@Override
	public RetryContext open(RetryContext parent) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("重试策略open, parent context=[{}].", parent);
		}
		RetryContext context = new DefaultRetryContext(parent);
		return context;
	}

	@Override
	public void close(RetryContext context) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("重试策略close, context=[{}].", context);
		}
	}

	@Override
	public void registerThrowable(RetryContext context, Throwable throwable) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("重试策略registerThrowable, context=[{}], throw=[{}].", context, throwable);
		}
		if (context instanceof DefaultRetryContext) {
			DefaultRetryContext retryContext = (DefaultRetryContext) context;
			retryContext.registerThrowable(throwable);
		}
	}

}
