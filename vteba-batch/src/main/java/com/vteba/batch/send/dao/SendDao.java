package com.vteba.batch.send.dao;

import com.vteba.batch.send.model.Send;
import com.vteba.tx.dao.spi.BasicDao;
import com.vteba.tx.jdbc.mybatis.annotation.DaoMapper;
import com.vteba.tx.jdbc.mybatis.annotation.DatabaseType;
import com.vteba.tx.jdbc.mybatis.annotation.Schema;

/**
 * 表send的MyBatis Dao Mapper。
 * 由代码工具自动生成，可以新增方法，但是不要修改自动生成的方法。
 * @date 2016-03-22 16:10:40
 */
@DaoMapper
public interface SendDao extends BasicDao<Send, Integer> {
	
	@Schema(DatabaseType.MASTER)
	public int saveSend(Send send);
}