package com.vteba.batch.user.retry;

import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryContext;

import com.vteba.batch.user.model.User;

/**
 * 重试执行完毕后，触发恢复回调操作，通常用在有状态的重试中。
 * 
 * @author yinlei
 * @date 2016年3月9日 上午10:49:42
 */
public class DefaultRecoveryCallback implements RecoveryCallback<User> {

	@Override
	public User recover(RetryContext context) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
