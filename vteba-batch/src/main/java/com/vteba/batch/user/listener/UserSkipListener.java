package com.vteba.batch.user.listener;

import javax.inject.Named;

import org.springframework.batch.core.SkipListener;

import com.vteba.batch.user.model.User;

@Named
public class UserSkipListener implements SkipListener<User, User> {

	@Override
	public void onSkipInRead(Throwable t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkipInWrite(User item, Throwable t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkipInProcess(User item, Throwable t) {
		// TODO Auto-generated method stub
		
	}

}
