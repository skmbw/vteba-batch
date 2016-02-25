package com.vteba.mq.rocketmq.model;

import java.io.Serializable;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;

import com.alibaba.rocketmq.common.message.MessageExt;

public class Message extends MessageExt implements Serializable {
	private static final long serialVersionUID = -9100620115355713412L;
	
	private Object bodyEntity;
	private MessageExt nativeMessage;

	public Message(Object bodyEntity, MessageExt nativeMessage) {
		super();
		this.bodyEntity = bodyEntity;
		this.nativeMessage = nativeMessage;
	}

	public <T> T getBodyEntity() {
		@SuppressWarnings("unchecked")
		T t = (T) bodyEntity;
		return t;
	}

	public void setBodyEntity(Object bodyEntity) {
		this.bodyEntity = bodyEntity;
	}

	public MessageExt getNativeMessage() {
		return nativeMessage;
	}

	public void setNativeMessage(MessageExt nativeMessage) {
		this.nativeMessage = nativeMessage;
	}

	public ByteBuffer getBornHostBytes() {
		return nativeMessage.getBornHostBytes();
	}

	public String getUserProperty(String name) {
		return nativeMessage.getUserProperty(name);
	}

	public ByteBuffer getStoreHostBytes() {
		return nativeMessage.getStoreHostBytes();
	}

	public String getProperty(String name) {
		return nativeMessage.getProperty(name);
	}

	public int getQueueId() {
		return nativeMessage.getQueueId();
	}

	public void setQueueId(int queueId) {
		nativeMessage.setQueueId(queueId);
	}

	public String getTopic() {
		return nativeMessage.getTopic();
	}

	public long getBornTimestamp() {
		return nativeMessage.getBornTimestamp();
	}

	public void setTopic(String topic) {
		nativeMessage.setTopic(topic);
	}

	public void setBornTimestamp(long bornTimestamp) {
		nativeMessage.setBornTimestamp(bornTimestamp);
	}

	public String getTags() {
		return nativeMessage.getTags();
	}

	public SocketAddress getBornHost() {
		return nativeMessage.getBornHost();
	}

	public void setTags(String tags) {
		nativeMessage.setTags(tags);
	}

	public String getBornHostString() {
		return nativeMessage.getBornHostString();
	}

	public String getKeys() {
		return nativeMessage.getKeys();
	}

	public void setKeys(String keys) {
		nativeMessage.setKeys(keys);
	}

	public void setKeys(Collection<String> keys) {
		nativeMessage.setKeys(keys);
	}

	public String getBornHostNameString() {
		return nativeMessage.getBornHostNameString();
	}

	public int getDelayTimeLevel() {
		return nativeMessage.getDelayTimeLevel();
	}

	public void setBornHost(SocketAddress bornHost) {
		nativeMessage.setBornHost(bornHost);
	}

	public long getStoreTimestamp() {
		return nativeMessage.getStoreTimestamp();
	}

	public void setStoreTimestamp(long storeTimestamp) {
		nativeMessage.setStoreTimestamp(storeTimestamp);
	}

	public void setDelayTimeLevel(int level) {
		nativeMessage.setDelayTimeLevel(level);
	}

	public SocketAddress getStoreHost() {
		return nativeMessage.getStoreHost();
	}

	public boolean isWaitStoreMsgOK() {
		return nativeMessage.isWaitStoreMsgOK();
	}

	public void setStoreHost(SocketAddress storeHost) {
		nativeMessage.setStoreHost(storeHost);
	}

	public String getMsgId() {
		return nativeMessage.getMsgId();
	}

	public void setMsgId(String msgId) {
		nativeMessage.setMsgId(msgId);
	}

	public void setWaitStoreMsgOK(boolean waitStoreMsgOK) {
		nativeMessage.setWaitStoreMsgOK(waitStoreMsgOK);
	}

	public int getSysFlag() {
		return nativeMessage.getSysFlag();
	}

	public void setSysFlag(int sysFlag) {
		nativeMessage.setSysFlag(sysFlag);
	}

	public int getFlag() {
		return nativeMessage.getFlag();
	}

	public int getBodyCRC() {
		return nativeMessage.getBodyCRC();
	}

	public void setFlag(int flag) {
		nativeMessage.setFlag(flag);
	}

	public void setBodyCRC(int bodyCRC) {
		nativeMessage.setBodyCRC(bodyCRC);
	}

	public byte[] getBody() {
		return nativeMessage.getBody();
	}

	public long getQueueOffset() {
		return nativeMessage.getQueueOffset();
	}

	public void setBody(byte[] body) {
		nativeMessage.setBody(body);
	}

	public void setQueueOffset(long queueOffset) {
		nativeMessage.setQueueOffset(queueOffset);
	}

	public Map<String, String> getProperties() {
		return nativeMessage.getProperties();
	}

	public long getCommitLogOffset() {
		return nativeMessage.getCommitLogOffset();
	}

	public void setCommitLogOffset(long physicOffset) {
		nativeMessage.setCommitLogOffset(physicOffset);
	}

	public void setBuyerId(String buyerId) {
		nativeMessage.setBuyerId(buyerId);
	}

	public int getStoreSize() {
		return nativeMessage.getStoreSize();
	}

	public String getBuyerId() {
		return nativeMessage.getBuyerId();
	}

	public void setStoreSize(int storeSize) {
		nativeMessage.setStoreSize(storeSize);
	}

	public int getReconsumeTimes() {
		return nativeMessage.getReconsumeTimes();
	}

	public void setReconsumeTimes(int reconsumeTimes) {
		nativeMessage.setReconsumeTimes(reconsumeTimes);
	}

	public long getPreparedTransactionOffset() {
		return nativeMessage.getPreparedTransactionOffset();
	}

	public void setPreparedTransactionOffset(long preparedTransactionOffset) {
		nativeMessage.setPreparedTransactionOffset(preparedTransactionOffset);
	}

	public String toString() {
		return nativeMessage.toString();
	}
	
	
}
