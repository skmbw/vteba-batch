package com.vteba.batch.user.policy;

import javax.inject.Named;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

@Named
public class UserSkipPolicy implements SkipPolicy {

	@Override
	public boolean shouldSkip(Throwable t, int skipCount) throws SkipLimitExceededException {
		// TODO Auto-generated method stub
		return false;
	}

}
