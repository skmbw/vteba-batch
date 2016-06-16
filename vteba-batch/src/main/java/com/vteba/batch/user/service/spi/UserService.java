package com.vteba.batch.user.service.spi;

import com.vteba.batch.user.model.User;
import com.vteba.batch.user.model.UserBean;
import com.vteba.common.service.MyBatisService;

/**
 * 用户相关的业务service接口。
 * @author yinlei
 * @date 2016-2-18 16:34:16
 */
public interface UserService extends MyBatisService<User, UserBean, Integer> {
	
	public int updateUser(int request);
	
	public int updateEntity(int request);
}
