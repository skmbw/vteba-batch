package com.vteba.batch.user.retry;

import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.BackOffContext;

public class BackOffContextImpl implements BackOffContext {
	private RetryContext retryContext;

	public BackOffContextImpl(RetryContext retryContext) {
		super();
		this.retryContext = retryContext;
	}

	public RetryContext getRetryContext() {
		return retryContext;
	}

	public void setRetryContext(RetryContext retryContext) {
		this.retryContext = retryContext;
	}
	
	
}
