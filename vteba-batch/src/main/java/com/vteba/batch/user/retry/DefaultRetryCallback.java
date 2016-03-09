package com.vteba.batch.user.retry;

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

	@Override
	public User doWithRetry(RetryContext context) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

}