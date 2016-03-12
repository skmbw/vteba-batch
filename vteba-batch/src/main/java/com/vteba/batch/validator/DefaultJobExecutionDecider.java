package com.vteba.batch.validator;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

/**
 * Job执行步骤决策器。根据Job和Step执行的情况，判断执行的情况。
 * 
 * @author yinlei
 * @date 2016年3月12日 下午3:37:01
 */
public class DefaultJobExecutionDecider implements JobExecutionDecider {

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
		// TODO Auto-generated method stub
		return null;
	}

}
