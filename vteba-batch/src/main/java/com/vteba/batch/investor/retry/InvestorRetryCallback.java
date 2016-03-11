package com.vteba.batch.investor.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;

import com.vteba.batch.investor.model.Investor;

/**
 * 投资者重试回调
 * 
 * @author yinlei
 * @date 2016年3月10日 下午8:46:05
 */
public class InvestorRetryCallback implements RetryCallback<Investor, Throwable> {
	private static final Logger LOGGER = LoggerFactory.getLogger(InvestorRetryCallback.class);
	
	@Override
	public Investor doWithRetry(RetryContext context) throws Throwable {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.debug("投资者Tasklet回调，context=[{}].", context);
		}
		return null;
	}

}
