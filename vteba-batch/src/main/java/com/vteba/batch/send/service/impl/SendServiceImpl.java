package com.vteba.batch.send.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.vteba.batch.send.dao.SendDao;
import com.vteba.batch.send.model.Send;
import com.vteba.batch.send.service.spi.SendService;
import com.vteba.common.service.impl.BasicServiceImpl;
import com.vteba.tx.dao.spi.BasicDao;

/**
 * 发送相关的service业务实现。
 * @author yinlei
 * @date 2016-3-22 16:10:39
 */
@Named
public class SendServiceImpl extends BasicServiceImpl<Send, Integer> implements SendService {
	
	private SendDao sendDao;
	
	@Override
	@Inject
	public void setBasicDao(BasicDao<Send, Integer> sendDao) {
		this.basicDao = sendDao;
		this.sendDao = (SendDao) sendDao;
	}

	@Override
	public int saveSend(Send send) {
		return sendDao.saveSend(send);
	}
}
