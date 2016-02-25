package com.vteba.mq.rocketmq.consumer;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.vteba.mq.rocketmq.listener.RocketMQMessageListener;
import com.vteba.mq.rocketmq.listener.RocketMQMessageListenerWrapper;
import com.vteba.utils.common.PropUtils;
import com.vteba.utils.serialize.Kryoer;

/**
 * RocketMQ消息消费者。对RocketMQ原生的消费者做了一些配置属性的扩展，方便配置。<br>
 * 1、建议一个消费者订阅一个Topic主题的多个Tag。不建议一个消费者同时订阅多个Topic。
 * 只订阅一个Topic，逻辑清晰，更有利于以后的代码扩展。<br>
 * 2、消费者要和业务监听器配对，不要出现Consumer订阅了，但是MessageListener没有处理
 * 的情况。
 * @author yinlei
 * @date 2016年2月24日 下午3:27:18
 */
public class RocketMQMessageConsumer implements InitializingBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(RocketMQMessageConsumer.class);
	// MQ地址
	private String namesrvAddr;
	// 消费者分组
	private String consumerGroup;
	// 订阅的主题，如果只订阅一个主题，建议使用这个属性，否则建议使用topicSubExpressionMap
	private String topic;
	// 订阅的主题的TAG，如果为null，默认*
	private String subExpression = "*";
	// 订阅多个Topic上的Tag时，设置此属性
	private Map<String, String> topicSubExpressionMap;
	// 业务消息监听器
	private RocketMQMessageListener messageListener;
	// RocketMQ的消费者
	private DefaultMQPushConsumer defaultMQPushConsumer;
	// 是否启用kryo来反序列化对象，默认true，要关闭，请设为false
	private boolean kryoFeatures = true;
	// kryo序列化器
	private Kryoer kryoer;
	// 强制在业务层上反序列化
	private boolean forceServiceDeserialize;
	
	/**
	 * 启动消费者
	 */
	public void init() {
		LOGGER.debug("启动RocketMQ消费者监听...");
		try {
			defaultMQPushConsumer = new DefaultMQPushConsumer();
			defaultMQPushConsumer.setConsumerGroup(consumerGroup);
			defaultMQPushConsumer.setNamesrvAddr(namesrvAddr);
			// 订阅主题为topic下Tag为subExpression的消息
			if (topic != null) {
				defaultMQPushConsumer.subscribe(topic, subExpression);
			}
			// 订阅了多个主题
			if (topicSubExpressionMap != null) {
				for (Entry<String, String> entry : topicSubExpressionMap.entrySet()) {
					defaultMQPushConsumer.subscribe(entry.getKey(), entry.getValue());
				}
			}
			// 程序第一次启动从消息队列头取数据
			defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
			RocketMQMessageListenerWrapper messageListenerWrapper = new RocketMQMessageListenerWrapper();
			messageListenerWrapper.setMessageListener(messageListener);
			// 强制业务层自己调用Kryoer去反序列化
			if (forceServiceDeserialize) {
				messageListenerWrapper.setKryoFeatures(false);
			} else {
				if (kryoFeatures) { // 开启了在统一的Kryo反序列化
					messageListenerWrapper.setKryoFeatures(kryoFeatures);
					messageListenerWrapper.setKryoer(kryoer);
				}
			}
			
			defaultMQPushConsumer.registerMessageListener(messageListenerWrapper);
			defaultMQPushConsumer.start();
			LOGGER.debug("启动RocketMQ消费者监听成功.");
		} catch (Exception e) {
			LOGGER.error("启动RocketMQ消费者监听异常，msg=[{}].", e);
		}
	}

	/**
	 * 清理消费者
	 */
	public void destroy() {
		if (defaultMQPushConsumer != null) {
			try {
				defaultMQPushConsumer.shutdown();
				LOGGER.debug("关闭RocketMQ消费者监听...");
			} catch (Exception e) {
				LOGGER.error("启动RocketMQ消费者监听异常，msg=[{}].", e);
			}
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(consumerGroup, "property[consumerGroup] cannot be null.");
		if (namesrvAddr == null) {
			// 此处，对于不同的配置可以做调整，例如获取ZK上的配置等
			namesrvAddr = PropUtils.get("rocketmq.namesrv.addr");
		}
		Assert.notNull(namesrvAddr, "property[namesrvAddr] cannot be null.");
		Assert.notNull(messageListener, "property[messageListener] of type[RocketMQMessageListener] cannot be null.");
		
		if (topic == null && topicSubExpressionMap == null) {
			throw new IllegalArgumentException("property[topic] and [topicSubExpressionMap] cannot be null together.");
		}
		
		// 序列化工具，如果不需要，可以关闭
		if (kryoer == null && kryoFeatures) {
			Assert.notNull(kryoer, "property[kryoer] cannot be null, or set property[kryoFeatures] to false, close this features.");
		}
	}

	public String getNamesrvAddr() {
		return namesrvAddr;
	}

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}

	public String getConsumerGroup() {
		return consumerGroup;
	}

	public void setConsumerGroup(String consumerGroup) {
		this.consumerGroup = consumerGroup;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getSubExpression() {
		return subExpression;
	}

	public void setSubExpression(String subExpression) {
		this.subExpression = subExpression;
	}

	public Map<String, String> getTopicSubExpressionMap() {
		return topicSubExpressionMap;
	}

	public void setTopicSubExpressionMap(Map<String, String> topicSubExpressionMap) {
		this.topicSubExpressionMap = topicSubExpressionMap;
	}

	public RocketMQMessageListener getMessageListener() {
		return messageListener;
	}

	public void setMessageListener(RocketMQMessageListener messageListener) {
		this.messageListener = messageListener;
	}

	public DefaultMQPushConsumer getDefaultMQPushConsumer() {
		return defaultMQPushConsumer;
	}

	public void setKryoFeatures(boolean kryoFeatures) {
		this.kryoFeatures = kryoFeatures;
	}

	public void setKryoer(Kryoer kryoer) {
		this.kryoer = kryoer;
	}

	public void setForceServiceDeserialize(boolean forceServiceDeserialize) {
		this.forceServiceDeserialize = forceServiceDeserialize;
	}
}
