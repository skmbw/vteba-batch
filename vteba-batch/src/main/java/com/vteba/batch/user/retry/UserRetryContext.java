package com.vteba.batch.user.retry;

import org.springframework.retry.RetryContext;
import org.springframework.retry.context.RetryContextSupport;

/**
 * 重试上下文，重试策略中open方法不能返回null的上下文，否则不会重试。
 * 
 * @author yinlei
 * @date 2016年3月11日 上午11:41:00
 */
public class UserRetryContext extends RetryContextSupport {
	private static final long serialVersionUID = 8910586445511959661L;

	public UserRetryContext(RetryContext parent) {
		super(parent);
	}

}
