package com.vteba.batch.launcher;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

import com.vteba.utils.date.DateUtils;

/**
 * Job实例委托类，将调用JobLauncher执行Job。
 * 
 * @author yinlei
 * @date 2016年2月10日 上午11:54:17
 */
public class JobInstanceDelegate {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JobInstanceDelegate.class);
	
	@Inject
	private JobLauncher jobLauncher;
	private String dateFormat;
	private Map<String, String> jobParameters;
	private Job job;

	public void execute() throws Exception {
		if (jobParameters == null) {
			jobParameters = new HashMap<String, String>();
		}
		// 不能用这个参数，参数和job name是唯一确定Job的
		jobParameters.put("executeDatetime", DateUtils.toDateString(dateFormat) + 8);
		JobParameters allParams = translateParams(job, jobParameters);

		JobExecution je = jobLauncher.run(job, allParams);
		Long jobExecutionId = je.getId();
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("开始执行：JobName=[" + job.getName() + "], JobExecutionId=[" + jobExecutionId + "]");
		}
	}

	private JobParameters translateParams(Job job, Map<String, String> params) {
		JobParametersBuilder builder = new JobParametersBuilder();

		if (params != null) {
			for (Map.Entry<String, String> param : params.entrySet()) {
				builder.addString(param.getKey(), param.getValue());
			}
		}

		return builder.toJobParameters();
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public void setJobParameters(Map<String, String> jobParameters) {
		this.jobParameters = jobParameters;
	}

	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
}
