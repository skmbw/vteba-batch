package com.vteba.test;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.vteba.mq.rocketmq.listener.RocketMQMessageListener;
import com.vteba.mq.rocketmq.model.Message;

public class SimpleRocketMQMessageListener implements RocketMQMessageListener {
	
	@Override
	public boolean onMessage(List<MessageExt> messageList, ConsumeConcurrentlyContext context) {
		for (MessageExt message : messageList) {
			System.out.println(message);
		}
		return false;
	}

	@Override
	public boolean onConsume(List<Message> messageList, ConsumeConcurrentlyContext context) {
		// TODO Auto-generated method stub
		return false;
	}

}
