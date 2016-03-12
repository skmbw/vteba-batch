package com.vteba.batch.listener;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.AfterChunkError;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * Chunk执行监听器.可以使用注解，也可以实现对应的接口。
 * 
 * @author yinlei
 * @date 2016年3月12日 上午11:32:19
 */
@Named
public class ChunkExecutionListener implements ChunkListener {
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
		
	}

}
