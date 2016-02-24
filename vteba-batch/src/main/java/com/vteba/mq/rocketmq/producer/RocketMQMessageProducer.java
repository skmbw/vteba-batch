package com.vteba.mq.rocketmq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

public class RocketMQMessageProducer {
	private static final Logger LOGGER = LoggerFactory.getLogger(RocketMQMessageProducer.class);

	private String namesrvAddr;
	private String producerGroup;
	private DefaultMQProducer mqProducer;

	public void init() {
		mqProducer = new DefaultMQProducer(producerGroup);
		mqProducer.setNamesrvAddr(namesrvAddr);
		try {
			mqProducer.start();
		} catch (Exception e) {
			LOGGER.error("启动RocketMQ生产者异常，msg=[{}].", e);
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param message 待发送消息
	 * @return 发送结果 SendResult
	 */
	public SendResult send(Message message)
			throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
		SendResult result = mqProducer.send(message);
		return result;
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

	public DefaultMQProducer getMqProducer() {
		return mqProducer;
	}
}
