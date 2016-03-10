package com.vteba.batch.user;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.vteba.batch.user.model.User;
import com.vteba.batch.user.retry.DefaultRetryCallback;
import com.vteba.batch.user.retry.UserRetryPolicy;

@Named
public class UserTasklet implements Tasklet {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserTasklet.class);
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext context) throws Exception {
		LOGGER.info("UserTasklet execute.");
		
		RetryCallback<User, Throwable> retryCallback = new DefaultRetryCallback();
		
		RetryPolicy retryPolicy = new UserRetryPolicy();
		
//		RetryListener[] retryListeners = { new UserRetryListener() };
//		
//		BackOffPolicy backOffPolicy = new DefaultBackOffPolicy();
		
		RetryTemplate retryTemplate = new RetryTemplate();
//		retryTemplate.setBackOffPolicy(backOffPolicy);
//		retryTemplate.setListeners(retryListeners);
		retryTemplate.setRetryPolicy(retryPolicy);
		
//		RecoveryCallback<User> recoveryCallback = new DefaultRecoveryCallback();
//		RetryState retryState = new DefaultRetryState();
		
		try {
//			retryTemplate.execute(retryCallback, recoveryCallback);
//			retryTemplate.execute(retryCallback, recoveryCallback, retryState);
			retryTemplate.execute(retryCallback);
		} catch (Throwable e) {
			
		}
		
//		throw new RuntimeException("UserTasklet");
		return RepeatStatus.FINISHED;
	}

}
