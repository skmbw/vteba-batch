package com.vteba.mq.rocketmq.listener;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * RocketMQ消息业务处理器监听器。对消息的业务处理，要实现这个接口。<br>
 * 1、隔离底层的接口，同时规范代码的写法。可能需要替换底层的MQ实现等，减少对业务代码的改动<br>
 * 2、如果以后有扩展，也可以通过该类入手。
 * 
 * @author yinlei
 * @date 2016年2月24日 下午2:11:55
 */
public interface RocketMQMessageListener {
	/**
	 * 处理消息
	 * @param messageList 消息list
	 * @param context 消费消息上下文
	 * @return true处理成功，false处理失败
	 */
	public boolean onMessage(List<MessageExt> messageList, ConsumeConcurrentlyContext context);
}
