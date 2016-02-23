package com.vteba.batch.user.parallel;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Named
public class ParallelStep1 implements Tasklet {
	private static final Logger LOGGER = LoggerFactory.getLogger(ParallelStep1.class);
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		LOGGER.info("并行步骤1完成。");
		return RepeatStatus.FINISHED;
	}

}
