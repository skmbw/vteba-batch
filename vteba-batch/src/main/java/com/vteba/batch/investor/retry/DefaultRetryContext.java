package com.vteba.batch.investor.retry;

import org.springframework.retry.RetryContext;
import org.springframework.retry.context.RetryContextSupport;

/**
 * 重试上下文，在重试策略中open打开的上下问不能为null，否则也会导致不重试。
 * 
 * @author yinlei
 * @date 2016年3月11日 上午11:39:46
 */
public class DefaultRetryContext extends RetryContextSupport {
	private static final long serialVersionUID = 5648357793677673234L;

	public DefaultRetryContext(RetryContext parent) {
		super(parent);
	}
}
