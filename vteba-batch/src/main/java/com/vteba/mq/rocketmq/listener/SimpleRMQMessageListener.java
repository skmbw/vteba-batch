package com.vteba.mq.rocketmq.listener;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.vteba.batch.user.model.User;
import com.vteba.mq.rocketmq.model.Message;
import com.vteba.utils.serialize.Kryoer;

@Named
public class SimpleRMQMessageListener implements RMQMessageListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleRMQMessageListener.class);
	
	// 用户topic
	private static final String TOPIC_USER = "YinleiUser";
	// 用户topic下的tags
	private static final String USER_TAG_TEST = "Test";
	private static final String USER_TAG_TEST1 = "Test1";
	
	// 支付topoc
	private static final String TOPIC_USER2 = "YinleiUser2";
	
	@Inject
	private Kryoer kryoer;
	
	@Override
	public boolean onMessage(MessageExt message, ConsumeConcurrentlyContext context) {
		String msgId = message.getMsgId();
		String topic = message.getTopic();
		String tags = message.getTags();
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("开始处理消息，msg=[{}]", message);
		}
		switch (topic) {
		case TOPIC_USER:
			if (USER_TAG_TEST.equals(tags)) {
				// 处理业务
				// 如果使用了Kryo，这里要使用Kryo反序列化回来
				User user = kryoer.fromByte(message.getBody());
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("处理用户信息，userId=[{}], userName=[{}].", user.getId(), user.getName());
				}
				
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("YinleiUser.Test消息处理成功，msgId=[{}], topic=[{}], tags=[{}].", msgId, topic, tags);
				}
			} else if (USER_TAG_TEST1.equals(tags)) {
				LOGGER.info("YinleiUser.Test1消息处理成功，msgId=[{}], topic=[{}], tags=[{}].", msgId, topic, tags);
			}
			break;
		case TOPIC_USER2:
			LOGGER.info("YinleiUser2.Test2消息处理成功，msgId=[{}], topic=[{}], tags=[{}].", msgId, topic, tags);
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean onMessage(Message message, ConsumeConcurrentlyContext context) {
		System.out.println(message);
		User user = message.getBodyEntity();
		System.out.println(user);
		return true;
	}

}
