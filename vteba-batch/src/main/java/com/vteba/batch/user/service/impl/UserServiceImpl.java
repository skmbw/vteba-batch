package com.vteba.batch.user.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.batch.user.dao.UserDao;
import com.vteba.batch.user.model.User;
import com.vteba.batch.user.model.UserBean;
import com.vteba.batch.user.service.spi.UserService;

import com.vteba.service.generic.impl.MyBatisServiceImpl;
import com.vteba.tx.jdbc.mybatis.spi.BaseDao;

/**
 * 用户相关的service业务实现。
 * @author yinlei
 * @date 2016-2-18 16:34:16
 */
@Named
public class UserServiceImpl extends MyBatisServiceImpl<User, UserBean, Integer> implements UserService {
	// 添加了方法后，记得改为private
	protected UserDao userDao;
	
	@Override
	@Inject
	public void setBaseDao(BaseDao<User, UserBean, Integer> userDao) {
		this.baseDao = userDao;
		this.userDao = (UserDao) userDao;
	}
}
