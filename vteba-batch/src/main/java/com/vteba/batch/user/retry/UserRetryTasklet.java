package com.vteba.batch.user.retry;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryListener;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.RetryState;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.vteba.batch.user.model.User;

/**
 * 通过RetryTemplate构建具有重试功能的Tasklet（默认没有重试功能）。chunk具有重试功能，也是使用RetryTemplate实现的重试功能。
 * 
 * @author yinlei
 * @date 2016年3月9日 上午10:39:39
 */
public class UserRetryTasklet implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		RetryCallback<User, Throwable> retryCallback = new DefaultRetryCallback();
		
		RetryPolicy retryPolicy = new UserRetryPolicy();
		
		RetryListener[] retryListeners = { new UserRetryListener() };
		
		BackOffPolicy backOffPolicy = new DefaultBackOffPolicy();
		
		RetryTemplate retryTemplate = new RetryTemplate();
		retryTemplate.setBackOffPolicy(backOffPolicy);
		retryTemplate.setListeners(retryListeners);
		retryTemplate.setRetryPolicy(retryPolicy);
		
		RecoveryCallback<User> recoveryCallback = new DefaultRecoveryCallback();
		RetryState retryState = new DefaultRetryState();
		
		try {
//			retryTemplate.execute(retryCallback, recoveryCallback);
			retryTemplate.execute(retryCallback, recoveryCallback, retryState);
		} catch (Throwable e) {
			
		}
		
		return RepeatStatus.FINISHED;
	}

}
