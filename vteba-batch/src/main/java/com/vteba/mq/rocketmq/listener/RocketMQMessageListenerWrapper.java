package com.vteba.mq.rocketmq.listener;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.vteba.mq.rocketmq.model.Message;
import com.vteba.utils.serialize.Kryoer;

/**
 * RocketMQ业务消息监听器包装类。隔离底层的接口，同时规范代码的写法。<br>
 * 如果以后有扩展，也可以通过该类入手，做一些拦截或者其他的处理等。
 * 
 * @author yinlei
 * @date 2016年2月24日 下午2:16:10
 */
public class RocketMQMessageListenerWrapper implements MessageListenerConcurrently {
	private RocketMQMessageListener messageListener;
	// 开启kryo序列化特性
	private boolean kryoFeatures;
	private Kryoer kryoer;
	
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageList, ConsumeConcurrentlyContext context) {
		boolean result = false;
		if (kryoFeatures) {
			List<Message> msgList = new ArrayList<Message>();
			// TODO YINLEI 后续调整
			// 在这里对消息体进行反序列化。最好是在底层进行，性能好。但是改动太大，暂时不这么做
			// 另外一种是在业务层上，需要时使用Kryo序列化避免创建Message对象和一次循环的开销
			for (MessageExt message : messageList) {
				Object bodyEntity = kryoer.fromByte(message.getBody());
				Message msg = new Message(bodyEntity, message);
				msgList.add(msg);
			}
			result = messageListener.onConsume(msgList, context);
		} else {
			result = messageListener.onMessage(messageList, context);
		}
		
		if (result) {
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		} else { // 处理失败，稍后重新尝试
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
	}

	public void setMessageListener(RocketMQMessageListener messageListener) {
		this.messageListener = messageListener;
	}

	public void setKryoFeatures(boolean kryoFeatures) {
		this.kryoFeatures = kryoFeatures;
	}

	public void setKryoer(Kryoer kryoer) {
		this.kryoer = kryoer;
	}
}
