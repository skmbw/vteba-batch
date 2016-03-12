package com.vteba.batch.listener;

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
public class ChunkExecutionListener implements ChunkListener {

	@BeforeChunk
	public void beforeChunk(ChunkContext context) {
		
	}
	
	@AfterChunk
	public void afterChunk(ChunkContext context) {
		
	}
	
	@Override
	@AfterChunkError
	public void afterChunkError(ChunkContext context) {
		
	}

}
