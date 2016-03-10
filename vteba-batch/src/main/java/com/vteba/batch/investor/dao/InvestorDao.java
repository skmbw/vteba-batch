package com.vteba.batch.investor.dao;

import com.vteba.batch.investor.model.Investor;
import com.vteba.tx.jdbc.mybatis.annotation.DaoMapper;
import com.vteba.tx.jdbc.mybatis.spi.BasicDao;

/**
 * 表investor的MyBatis Dao Mapper。
 * 由代码工具自动生成，可以新增方法，但是不要修改自动生成的方法。
 * @date 2016-03-10 14:08:24
 */
@DaoMapper
public interface InvestorDao extends BasicDao<Investor, Integer> {
}