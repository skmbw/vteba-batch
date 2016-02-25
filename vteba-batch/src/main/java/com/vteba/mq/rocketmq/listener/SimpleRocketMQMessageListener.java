package com.vteba.mq.rocketmq.listener;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.vteba.batch.user.model.User;
import com.vteba.mq.rocketmq.listener.RocketMQMessageListener;
import com.vteba.mq.rocketmq.model.Message;
import com.vteba.utils.serialize.Kryoer;

@Named
public class SimpleRocketMQMessageListener implements RocketMQMessageListener {
	@Inject
	private Kryoer kryoer;
	
	@Override
	public boolean onMessage(List<MessageExt> messageList, ConsumeConcurrentlyContext context) {
		for (MessageExt message : messageList) {
			System.out.println(message.getTopic());
			System.out.println(message.getTags());
			System.out.println(message);
			User user = kryoer.fromByte(message.getBody());
			System.out.println(user);
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
