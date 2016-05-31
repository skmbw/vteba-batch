package com.vteba.mq.rocketmq.listener;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.vteba.batch.user.model.User;
import com.vteba.mq.rocketmq.model.Message;
import com.vteba.utils.serialize.kryo.Kryoer;

@Named
public class SimpleRMQMessageListener implements RMQMessageListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleRMQMessageListener.class);
	
	// 用户topic
	private static final String TOPIC = "RMQTopic";
	// 用户topic下的tags
	private static final String TAG = "RMQTags";
	
	@Inject
	private Kryoer kryoer;
	
	@Override
	public boolean onMessage(MessageExt message, ConsumeConcurrentlyContext context) {
		String msgId = message.getMsgId();
		String topic = message.getTopic();
		String tags = message.getTags();
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("开始处理RMQ消息，msg=[{}]", message);
		}
		switch (topic) {
		case TOPIC:
			if (TAG.equals(tags)) {
				// 处理业务
				// 如果使用了Kryo，这里要使用Kryo反序列化回来
				User user = kryoer.fromByte(message.getBody());
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("处理用户信息，userId=[{}], userName=[{}].", user.getId(), user.getName());
				}
				
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("消息处理成功，msgId=[{}], topic=[{}], tags=[{}].", msgId, topic, tags);
				}
			}
			break;
		default:
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("未监听的Topic{}，{}，{}。", msgId, topic, tags);
			}
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

	@Override
	public boolean onMessage(MessageExt message, ConsumeOrderlyContext context) {
		// TODO Auto-generated method stub
		return false;
	}

}
