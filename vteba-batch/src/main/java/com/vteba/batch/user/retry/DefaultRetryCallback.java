package com.vteba.batch.user.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;

import com.vteba.batch.user.model.User;

/**
 * 重试回调接口，当发生重试时，会调用该接口完成重试的业务逻辑。
 * 
 * @author yinlei
 * @date 2016年3月9日 上午10:43:40
 */
public class DefaultRetryCallback implements RetryCallback<User, Throwable> {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRetryCallback.class);
	
	@Override
	public User doWithRetry(RetryContext context) throws Throwable {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.debug("重试上下文[{}].", context);
		}
		return null;
	}

}
