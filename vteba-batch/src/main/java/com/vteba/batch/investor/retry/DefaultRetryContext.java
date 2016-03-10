package com.vteba.batch.investor.retry;

import org.springframework.retry.RetryContext;
import org.springframework.retry.context.RetryContextSupport;

public class DefaultRetryContext extends RetryContextSupport {
	private static final long serialVersionUID = 5648357793677673234L;

	public DefaultRetryContext(RetryContext parent) {
		super(parent);
	}
}
