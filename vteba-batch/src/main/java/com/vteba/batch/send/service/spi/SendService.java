package com.vteba.batch.send.service.spi;

import com.vteba.batch.send.model.Send;
import com.vteba.common.service.BasicService;

/**
 * 发送相关的业务service接口。
 * @author yinlei
 * @date 2016-3-22 16:10:39
 */
public interface SendService extends BasicService<Send, Integer> {

	public int saveSend(Send send);
}
