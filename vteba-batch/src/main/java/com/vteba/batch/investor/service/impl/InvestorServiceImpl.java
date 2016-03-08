package com.vteba.batch.investor.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.batch.investor.dao.InvestorDao;
import com.vteba.batch.investor.model.Investor;
import com.vteba.batch.investor.service.spi.InvestorService;

import com.vteba.service.generic.impl.BasicServiceImpl;
import com.vteba.tx.jdbc.mybatis.spi.BasicDao;

/**
 * 投资人相关的service业务实现。
 * @author yinlei
 * @date 2016-3-8 14:19:05
 */
@Named
public class InvestorServiceImpl extends BasicServiceImpl<Investor, Integer> implements InvestorService {
	// 添加了方法后，记得改为private
	protected InvestorDao investorDao;
	
	@Override
	@Inject
	public void setBasicDao(BasicDao<Investor, Integer> investorDao) {
		this.basicDao = investorDao;
		this.investorDao = (InvestorDao) investorDao;
	}
}
