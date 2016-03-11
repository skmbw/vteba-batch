package com.vteba.batch.investor.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;

import com.vteba.batch.investor.model.Investor;

/**
 * 投资者重试回调。如果开启了重试策略，那么Tasklet的业务应该写在回调当中。
 * 
 * @author yinlei
 * @date 2016年3月10日 下午8:46:05
 */
public class InvestorRetryCallback implements RetryCallback<Investor, Throwable> {
	private static final Logger LOGGER = LoggerFactory.getLogger(InvestorRetryCallback.class);
	
	@Override
	public Investor doWithRetry(RetryContext context) throws Throwable {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("投资者Tasklet回调，context=[{}].", context);
		}
		int i = 2;
		if (i == 1) {
			throw new RuntimeException("测试异常重试。");
		}
		return null;
	}

}
