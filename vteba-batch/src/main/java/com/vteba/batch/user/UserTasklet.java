package com.vteba.batch.user;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Named
public class UserTasklet implements Tasklet {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserTasklet.class);
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext context) throws Exception {
		LOGGER.info("UserTasklet execute.");
		return RepeatStatus.FINISHED;
	}

}
