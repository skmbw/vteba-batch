package com.vteba.batch.listener;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.annotation.AfterProcess;
import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.core.annotation.OnProcessError;

import com.alibaba.rocketmq.common.message.Message;
import com.vteba.common.exception.ServiceException;

/**
 * 步骤step处理器监听器。可以使用注解，也可以实现对应的监听器。和chunk放在相同的位置。Read和Write应该也是的。
 * 
 * @author yinlei
 * @date 2016年3月12日 上午11:08:09
 */
@Named
public class ProcessExecutionListener<T, S> extends AbstractExecutionListener implements ItemProcessListener<T, S> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessExecutionListener.class);
	
	@BeforeProcess
	public void beforeProcess(T item) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("ProcessExecutionListener start");
		}
	}
	
	@AfterProcess
	public void afterProcess(T item, S result) {
		
	}

	@Override
	@OnProcessError
	public void onProcessError(T item, Exception e) {
		// 参数 T item就是正在处理的对象，就是哪个数据出错了，具体的业务要指定具体的实体类
		
		// 根据抛出的异常进行相应的处理
		if (e instanceof ServiceException) { // 服务异常，需要处理的
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("item处理出现异常.", e);
			}
			
			// 发送短信，或者邮件
			Message message = new Message("topic", "tags", new byte[0]);
			try {
				rocketMQMessageProducer.send(message);
			} catch (Exception e1) {
				LOGGER.error("send mq message error.", e1);
			}
		} else { // 其他的异常处理
			
		}
	}
}
