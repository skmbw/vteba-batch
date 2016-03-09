package com.vteba.batch.user.service.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vteba.batch.user.dao.UserDao;
import com.vteba.batch.user.model.User;
import com.vteba.batch.user.model.UserBean;
import com.vteba.batch.user.service.spi.UserService;
import com.vteba.service.generic.impl.MyBatisServiceImpl;
import com.vteba.tx.jdbc.mybatis.spi.BaseDao;
import com.vteba.utils.date.DateUtils;

/**
 * 用户相关的service业务实现。
 * @author yinlei
 * @date 2016-2-18 16:34:16
 */
@Named
public class UserServiceImpl extends MyBatisServiceImpl<User, UserBean, Integer> implements UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	// 添加了方法后，记得改为private
	protected UserDao userDao;
	
//	private byte[] syncLock = new byte[0]; // 这样定义对象锁开销最低，比new Object()低
	
	private ReentrantLock lock = new ReentrantLock();
	
	@Override
	@Inject
	public void setBaseDao(BaseDao<User, UserBean, Integer> userDao) {
		this.baseDao = userDao;
		this.userDao = (UserDao) userDao;
	}

	@Override
	public int updateUser(int request) {
		System.out.println(request + Thread.currentThread().getName() + ":" + DateUtils.toDateString("yyyyMMddHHmmssSSS"));
		int i = 0;
		try {
			lock.lock();
			//i = doUpdate(request);
			User user = new User();
			user.setAge(16);
			userDao.countBy(user);
			System.out.println(request + Thread.currentThread().getName() + ":" + DateUtils.toDateString("yyyyMMddHHmmssSSS"));
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				LOGGER.error("线程中断异常，msg=[{}]", e);
			}
			i = 1;
		} finally {
			lock.unlock();
		}
//		synchronized (syncLock) {
//			
//		}
		return i;
	}
	
	public int doUpdate(int request) {
		User user = new User();
		user.setAge(16);
		userDao.countBy(user);
		System.out.println(request + Thread.currentThread().getName() + ":" + DateUtils.toDateString("yyyyMMddHHmmssSSS"));
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			LOGGER.error("线程中断异常，msg=[{}]", e);
		}
		return 1;
	}

	//@Transactional(rollbackFor = {BusinessService.class})
	@Override
	public int updateEntity(int request) {
		User user = new User();
		user.setAge(35);
		user.setName("dalei");
		user.setUpdateDate(new Date());
		userDao.save(user);
		int i = 1;
		if (i == 1) {
//			throw new BasicException("基础异常");
//			throw new ServiceException("事务异常");
			// throw new BusinessService("事务异常子类");
			//throw new RuntimeException("运行时异常");
//			throw new BaseException("基础异常子类");
		}
		return 1;
	}
}
