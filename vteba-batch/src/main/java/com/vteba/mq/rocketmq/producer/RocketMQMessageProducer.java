package com.vteba.mq.rocketmq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.vteba.utils.serialize.Kryoer;

/**
 * RocketMQ生产者封装。主要的改进有：<br>
 * 1、对Message Body序列化的处理，可以直接传递JavaBean对象，使用Kryo将其
 * 序列化成字节数组。<br>
 * 2、该生产者可以配置默认的Topic和Tags，否则需要通过参数或者Message传入<br>
 * 3、重载了一些便捷的方法。但是注意参数的顺序，不清楚，记得看注释。
 * 
 * @author yinlei
 * @date 2016年2月25日 上午9:25:19
 */
public class RocketMQMessageProducer implements InitializingBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(RocketMQMessageProducer.class);
	// MQ地址/名服务
	private String namesrvAddr;
	// 生产者分组
	private String producerGroup;
	// 获取RocketMQ的生产者，进行其他的操作
	private DefaultMQProducer defaultMQProducer;
	// 该生产者默认的主题，也可以通过Message或者方法参数传入
	private String topic;
	// 该生产者默认的Tags
	private String tags;
	
	/*=============其他的默认属性=============*/
	private int defaultTopicQueueNums = 4; // 一个Topic下默认的队列数
    private int sendMsgTimeout = 3000; // 消息发送超时，3秒
    private int compressMsgBodyOverHowmuch = 1024 * 4; // 冲过4Kb压缩消息体
    private int retryTimesWhenSendFailed = 2; // 发送失败时的尝试次数
    private boolean retryAnotherBrokerWhenNotStoreOK = false; // 存储失败时，尝试另外的Broker
    private int maxMessageSize = 1024 * 128; // 最大消息大小，默认128Kb
    /*=============其他的默认属性=============*/
	
	private Kryoer kryoer; // Kryo序列化器
	// 默认需要Kryo，若要关闭，将该属性设为false
	private boolean kryoFeatures =  true;
	
	/**
	 * 根据配置初始化DefaultMQProducer
	 */
	public void init() {
		LOGGER.debug("启动RocketMQ生产者...");
		defaultMQProducer = new DefaultMQProducer(producerGroup);
		defaultMQProducer.setNamesrvAddr(namesrvAddr);
		
		defaultMQProducer.setDefaultTopicQueueNums(defaultTopicQueueNums);
		defaultMQProducer.setSendMsgTimeout(sendMsgTimeout);
		defaultMQProducer.setCompressMsgBodyOverHowmuch(compressMsgBodyOverHowmuch);
		defaultMQProducer.setRetryTimesWhenSendFailed(retryTimesWhenSendFailed);
		defaultMQProducer.setRetryAnotherBrokerWhenNotStoreOK(retryAnotherBrokerWhenNotStoreOK);
		defaultMQProducer.setMaxMessageSize(maxMessageSize);
		
		try {
			defaultMQProducer.start();
			LOGGER.debug("启动RocketMQ生产者成功。");
		} catch (Exception e) {
			LOGGER.error("启动RocketMQ生产者异常，msg=[{}].", e);
		}
	}

	/**
	 * 清理DefaultMQProducer
	 */
	public void destroy() {
		if (defaultMQProducer != null) {
			try {
				defaultMQProducer.shutdown();
				LOGGER.debug("关闭RocketMQ生产者...");
			} catch (Exception e) {
				LOGGER.error("关闭RocketMQ生产者异常，msg=[{}].", e);
			}
		}
	}
	
	/**
	 * 发送消息。这里的消息体要自己序列化成字节数组。
	 * 
	 * @param message 待发送消息
	 * @return 发送结果 SendResult
	 */
	public SendResult send(Message message)
			throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
		SendResult result = defaultMQProducer.send(message);
		return result;
	}

	/**
	 * 发送消息，Topic和Tags使用默认配置的，如果没有配置默认的Topic和Tags<br>
	 * 请不要使用该方法。可能导致消息丢失或者异常。
	 * @param message 待发送消息
	 * @return 发送结果 SendResult
	 * @throws Exception
	 */
	public SendResult send(Object message) throws Exception {
		return send(topic, tags, null, message);
	}
	
//	/**
//	 * 应该不会出现这种业务组合吧，没有默认的Topic，但是有默认的Tags
//	 * @param message 待发送消息
//	 * @param topic Topic
//	 * @return 发送结果
//	 * @throws Exception
//	 */
//	@Deprecated
//	public SendResult send(Object message, String topic) throws Exception {
//		return send(topic, tags, null, message);
//	}
	
	/**
	 * 发送消息
	 * @param tags 子标记
	 * @param message 待发送消息
	 * @return 发送结果
	 * @throws Exception
	 */
	public SendResult send(String tags, Object message) throws Exception {
		return send(topic, tags, null, message);
	}
	
	/**
	 * 发送消息
	 * @param topic 主题
	 * @param tags 子标记
	 * @param message 待发送消息
	 * @return 发送结果
	 * @throws Exception
	 */
	public SendResult send(String topic, String tags, Object message) throws Exception {
		return send(topic, tags, null, message);
	}
	
	/**
	 * 发送消息。存在重载冲突，<em><strong>调换了message的参数位置。</strong></em>
	 * @param message 待发送消息
	 * @param tags 子标记
	 * @param keys 消息唯一键
	 * @return 发送结果
	 * @throws Exception
	 */
	public SendResult send(Object message, String tags, String keys) throws Exception {
		return send(topic, tags, keys, message);
	}
	
	/**
	 * 发送消息，消息体使用了Kryo将对象序列化成字节数组。
	 * @param topic 主题
	 * @param tags 子标记
	 * @param keys 消息唯一键
	 * @param message 待发送消息
	 * @return 发送结果
	 * @throws Exception
	 */
	public SendResult send(String topic, String tags, String keys, Object message) throws Exception {
		// 这里是否要做判断呢？理论上来说，是需要的，但是如果我们知道的话，其实可以不需要，提升性能
		if (!kryoFeatures) {
			throw new IllegalArgumentException("kryo features is closed. if need, please set property[kryoFeatures] to true and set property[kryoer].");
		}
		byte[] body = kryoer.toByte(message);
		Message msg = new Message(topic, tags, keys, body);
		return send(msg);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(namesrvAddr, "property[namesrvAddr] cannot be null");
		Assert.notNull(producerGroup, "property[producerGroup] cannot be null");
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

	public String getProducerGroup() {
		return producerGroup;
	}

	public void setProducerGroup(String producerGroup) {
		this.producerGroup = producerGroup;
	}

	/**
	 * 因为只封装了send发送方法，可以通过该方法获得底层的RocketMQ的DefaultMQProducer，做一些其他的操作。
	 * @return DefaultMQProducer
	 */
	public DefaultMQProducer getDefaultMQProducer() {
		return defaultMQProducer;
	}

	/**
	 * 设置默认Topic
	 * @param topic
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * 设置默认Tags
	 * @param tags
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	public void setKryoer(Kryoer kryoer) {
		this.kryoer = kryoer;
	}

	public void setKryoFeatures(boolean kryoFeatures) {
		this.kryoFeatures = kryoFeatures;
	}

	public void setDefaultTopicQueueNums(int defaultTopicQueueNums) {
		this.defaultTopicQueueNums = defaultTopicQueueNums;
	}

	public void setSendMsgTimeout(int sendMsgTimeout) {
		this.sendMsgTimeout = sendMsgTimeout;
	}

	public void setCompressMsgBodyOverHowmuch(int compressMsgBodyOverHowmuch) {
		this.compressMsgBodyOverHowmuch = compressMsgBodyOverHowmuch;
	}

	public void setRetryTimesWhenSendFailed(int retryTimesWhenSendFailed) {
		this.retryTimesWhenSendFailed = retryTimesWhenSendFailed;
	}

	public void setRetryAnotherBrokerWhenNotStoreOK(boolean retryAnotherBrokerWhenNotStoreOK) {
		this.retryAnotherBrokerWhenNotStoreOK = retryAnotherBrokerWhenNotStoreOK;
	}

	public void setMaxMessageSize(int maxMessageSize) {
		this.maxMessageSize = maxMessageSize;
	}
}
