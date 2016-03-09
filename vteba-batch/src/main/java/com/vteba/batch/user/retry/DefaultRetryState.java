package com.vteba.batch.user.retry;

import org.springframework.retry.RetryState;

/**
 * 重试状态，用在有状态的重试中。可以根据key获取重试上下文。
 * 
 * @author yinlei
 * @date 2016年3月9日 上午10:51:36
 */
public class DefaultRetryState implements RetryState {

	@Override
	public Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isForceRefresh() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rollbackFor(Throwable exception) {
		// TODO Auto-generated method stub
		return false;
	}

}
