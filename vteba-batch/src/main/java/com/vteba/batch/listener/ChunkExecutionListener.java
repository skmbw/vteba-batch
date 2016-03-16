package com.vteba.batch.listener;

import java.util.Map;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.AfterChunkError;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;

/**
 * Chunk执行监听器.可以使用注解，也可以实现对应的接口。
 * 
 * @author yinlei
 * @date 2016年3月12日 上午11:32:19
 */
@Named
public class ChunkExecutionListener extends AbstractExecutionListener implements ChunkListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(ChunkExecutionListener.class);
	
	@BeforeChunk
	public void beforeChunk(ChunkContext context) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("before to execute chunk=[{}].", context);
		}
	}
	
	@AfterChunk
	public void afterChunk(ChunkContext context) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("after to execute chunk=[{}].", context);
		}
	}
	
	@Override
	@AfterChunkError
	public void afterChunkError(ChunkContext context) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("afterChunkError chunkContext=[{}].", context);
		}
		
		// step上下文
		StepContext stepContext = context.getStepContext();
		// step execution // batch_step_execution
		StepExecution  stepExecution = stepContext.getStepExecution();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("stepExecution=[{}].", stepExecution);
		}
		String stepName = stepContext.getStepName();
		// 该step对应的job参数
		Map<String, Object> jobParameters = stepContext.getJobParameters();
		// 该step对应的job执行上下文
		Map<String, Object> jobExecutionContext = stepContext.getJobExecutionContext();
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("stepName=[{}], jobParameters=[{}], jobExecutionContext=[{}].", 
					stepName, jobParameters, jobExecutionContext);
		}
		
		// step执行上下文 // batch_step_execution_context
		Map<String, Object> stepExecutionContext = stepContext.getStepExecutionContext();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("stepExecutionContext=[{}].", stepExecutionContext);
		}
	}

}
