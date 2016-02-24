package com.vteba.mq.rocketmq.listener;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * RocketMQ业务消息监听器包装类。隔离底层的接口，同时规范代码的写法。<br>
 * 如果以后有扩展，也可以通过该类入手，做一些拦截或者其他的处理等。
 * 
 * @author yinlei
 * @date 2016年2月24日 下午2:16:10
 */
public class RocketMQMessageListenerWrapper implements MessageListenerConcurrently {
	private RocketMQMessageListener messageListener;
	
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageList, ConsumeConcurrentlyContext context) {
		boolean result = messageListener.onMessage(messageList, context);
		if (result) {
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		} else { // 处理失败，稍后重新尝试
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
	}

	public RocketMQMessageListener getMessageListener() {
		return messageListener;
	}

	public void setMessageListener(RocketMQMessageListener messageListener) {
		this.messageListener = messageListener;
	}

}
