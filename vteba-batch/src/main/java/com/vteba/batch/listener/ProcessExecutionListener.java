package com.vteba.batch.listener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;

import com.vteba.batch.send.model.Send;
import com.vteba.batch.send.service.spi.SendService;
import com.vteba.batch.user.model.User;
import com.vteba.common.exception.BasicException;

/**
 * 步骤step处理器监听器。可以使用注解，也可以实现对应的监听器。和chunk放在相同的位置。Read和Write应该也是的。
 * 
 * @author yinlei
 * @date 2016年3月12日 上午11:08:09
 */
@Named
public class ProcessExecutionListener extends AbstractExecutionListener implements ItemProcessListener<User, User> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessExecutionListener.class);
	
	@Inject
	private SendService sendServiceImpl;
	
	private ExecutorService executor = Executors.newCachedThreadPool();
	
	public void beforeProcess(User item) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("ProcessExecutionListener start");
		}
	}
	
	public void afterProcess(User item, User result) {
		
	}

	@Override
	public void onProcessError(User item, Exception e) {
		// 参数 T item就是正在处理的对象，就是哪个数据出错了，具体的业务要指定具体的实体类
		
		// 根据抛出的异常进行相应的处理
		if (e instanceof BasicException) { // 服务异常，需要处理的
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("item处理出现异常.", e);
			}
			
			// 发送短信，或者邮件
//			Message message = new Message("topic", "tags", new byte[0]);
//			try {
//				rocketMQMessageProducer.send(message);
//			} catch (Exception e1) {
//				LOGGER.error("send mq message error.", e1);
//			}
			Send send = new Send();
			send.setName("yinlei");
			
			// spring的事务是绑定到当前线程的，出现异常了当前线程的事务标记为rollback，
			// listener和ItemProcess是在同一个线程上执行的，如果不开启新的线程，会导致和ItemProcess一起回滚数据
			Task task = new Task();
			task.setSend(send);
			task.setSendServiceImpl(sendServiceImpl);
			
			executor.submit(task);
		} else { // 其他的异常处理
			
		}
	}
	
	public class Task implements Runnable {
		private SendService sendServiceImpl;
		private Send send;
		
		public void run() {
			sendServiceImpl.save(send);
		}

		public void setSendServiceImpl(SendService sendServiceImpl) {
			this.sendServiceImpl = sendServiceImpl;
		}

		public void setSend(Send send) {
			this.send = send;
		}
	}
}
