package com.vteba.mq.rocketmq.listener;

import java.util.List;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.vteba.batch.user.model.User;
import com.vteba.mq.rocketmq.model.Message;
import com.vteba.utils.serialize.Kryoer;

@Named
public class SimpleRocketMQMessageListener implements RocketMQMessageListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleRocketMQMessageListener.class);
	
	// 用户topic
	private static final String TOPIC_USER = "YinleiUser";
	// 用户topic下的tags
	private static final String USER_TAG_TEST = "Test";
	private static final String USER_TAG_TEST1 = "Test1";
	
	// 支付topoc
	private static final String TOPIC_PAY = "TOPIC_PAY";
	
//	@Inject
	private Kryoer kryoer;
	
	
	@Override
	public boolean onMessage(List<MessageExt> messageList, ConsumeConcurrentlyContext context) {
		for (MessageExt message : messageList) {
			String msgId = message.getMsgId();
			String topic = message.getTopic();
			String tags = message.getTags();
			switch (topic) {
			case TOPIC_USER:
				if (USER_TAG_TEST.equals(tags)) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("开始处理消息，msgId=[{}], topic=[{}], tags=[{}].", msgId, topic, tags);
					}
					
					// 处理业务
					// 如果使用了Kryo，这里要使用Kryo反序列化回来
					User user = kryoer.fromByte(message.getBody());
					if (LOGGER.isInfoEnabled()) {
						LOGGER.info("处理用户信息，userId=[{}], userName=[{}].", user.getId(), user.getName());
					}
					
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("消息处理成功，msgId=[{}], topic=[{}], tags=[{}].", msgId, topic, tags);
					}
				} else if (USER_TAG_TEST1.equals(tags)) {
					
				}
				break;
			case TOPIC_PAY:
				
				break;
			default:
				break;
			}
		}
		return true;
	}

	@Override
	public boolean onConsume(List<Message> messageList, ConsumeConcurrentlyContext context) {
		for (Message message : messageList) {
			System.out.println(message.getTopic());
			System.out.println(message.getTags());
			System.out.println(message);
			User user = message.getBodyEntity();
			System.out.println(user);
		}
		return true;
	}

}
