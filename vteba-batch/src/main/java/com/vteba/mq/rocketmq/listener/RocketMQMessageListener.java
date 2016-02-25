package com.vteba.mq.rocketmq.listener;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.vteba.mq.rocketmq.model.Message;

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
	 * 处理消息。<br>
	 * 1、如果没有开启Kryo序列化特性，请实现该方法。<br>
	 * 2、或者开启了Kryo特性，但是强制在service层序列化，也要实现这个方法。<br>
	 * 3、这个方式性能好，不需要做额外的构造对象和一次循环的开销。（其实也很小啦）
	 * @param messageList 消息list
	 * @param context 消费消息上下文
	 * @return true处理成功，false处理失败
	 */
	public boolean onMessage(List<MessageExt> messageList, ConsumeConcurrentlyContext context);
	
	/**
	 * 处理消息。如果开启了Kryo序列化特性，请实现该方法。<br>
	 * 自定义了一个Message对象，和MessageExt一样，多了一个body的对象属性<br>
	 * 主要的开销是：构造Message对象、多一次循环
	 * @param messageList 消息list
	 * @param context 消费消息上下文
	 * @return true处理成功，false处理失败
	 */
	public boolean onConsume(List<Message> messageList, ConsumeConcurrentlyContext context);;
}
