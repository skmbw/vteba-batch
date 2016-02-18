package com.vteba.batch.user.dao;

import com.vteba.batch.user.model.User;
import com.vteba.batch.user.model.UserBean;
import com.vteba.tx.jdbc.mybatis.annotation.DaoMapper;
import com.vteba.tx.jdbc.mybatis.spi.BaseDao;

/**
 * 表user的MyBatis Dao Mapper。
 * 由代码工具自动生成，可以新增方法，但是不要修改自动生成的方法。
 * @date 2016-02-18 16:34:17
 */
@DaoMapper
public interface UserDao extends BaseDao<User, UserBean, Integer> {
}