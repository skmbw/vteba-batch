package com.vteba.batch.user.retry;

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
public class UserRetryPolicy implements RetryPolicy {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRetryPolicy.class);
	
	@Override
	public boolean canRetry(RetryContext context) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("重试策略canRetry：[{}].", context);
		}
		return true;
	}

	@Override
	public RetryContext open(RetryContext parent) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("重试策略open：[{}].", parent);
		}
		return parent;
	}

	@Override
	public void close(RetryContext context) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("重试策略close：[{}].", context);
		}
	}

	@Override
	public void registerThrowable(RetryContext context, Throwable throwable) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("重试策略registerThrowable：[{}], throw=[{}].", context, throwable);
		}		
	}

}
