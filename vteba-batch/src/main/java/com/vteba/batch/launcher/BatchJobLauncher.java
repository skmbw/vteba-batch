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

public class BatchJobLauncher {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BatchJobLauncher.class);
	
	@Inject
	private JobLauncher jobLauncher;

	private Map<String, String> jobParameters;

	private Job job;

	public void execute() throws Exception {
		if (jobParameters == null) {
			jobParameters = new HashMap<String, String>();
		}
		jobParameters.put("now", Long.toString(System.currentTimeMillis()));
		JobParameters allParams = translateParams(job, jobParameters);

		JobExecution je = jobLauncher.run(job, allParams);
		Long jobExecutionId = je.getId();
		LOGGER.info("开始执行:" + job.getName() + "---" + jobExecutionId);
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

	public Map<String, String> getJobParameters() {
		return jobParameters;
	}

	public void setJobParameters(Map<String, String> jobParameters) {
		this.jobParameters = jobParameters;
	}

	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}
}
