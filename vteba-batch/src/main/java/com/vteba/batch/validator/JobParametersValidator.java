package com.vteba.batch.validator;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;

/**
 * Job参数验证器
 * 
 * @author yinlei
 * @date 2016年3月12日 下午3:35:56
 */
public class JobParametersValidator implements org.springframework.batch.core.JobParametersValidator {

	@Override
	public void validate(JobParameters parameters) throws JobParametersInvalidException {
		
	}

}
