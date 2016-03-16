package com.vteba.batch.listener;

import com.vteba.mq.rocketmq.producer.RocketMQMessageProducer;

/**
 * 监听器的抽象Listener，一些公共的属性
 * @author yinlei 
 * @date 2016-3-16
 */
public abstract class AbstractExecutionListener {
    
    /** 消息生产者 */
    protected RocketMQMessageProducer rocketMQMessageProducer;

    public void setRocketMQMessageProducer(RocketMQMessageProducer rocketMQMessageProducer) {
        this.rocketMQMessageProducer = rocketMQMessageProducer;
    }
}
