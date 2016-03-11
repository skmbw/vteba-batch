package com.vteba.batch.investor.retry;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.retry.support.RetryTemplate;

/**
 * 可重试的Tasklet
 * 
 * @author yinlei
 * @date 2016年3月10日 下午8:42:57
 */
@Named
public class InvestorRetryTasklet implements Tasklet {
	private static final Logger LOGGER = LoggerFactory.getLogger(InvestorRetryTasklet.class);
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		InvestorRetryPolicy retryPolicy = new InvestorRetryPolicy();
		
		RetryTemplate template = new RetryTemplate();
		template.setRetryPolicy(retryPolicy);
		
		InvestorRetryCallback retryCallback = new InvestorRetryCallback();
		
		try {
			template.execute(retryCallback);
		} catch (Throwable e) {
			LOGGER.error("执行重试错误，msg=[{}].", e.getMessage());
		}
		
//		int i = 1;
//		if (i == 1) {
//			throw new ServiceException("测试异常重试。");
//		}
		
		return RepeatStatus.FINISHED;
	}

}
