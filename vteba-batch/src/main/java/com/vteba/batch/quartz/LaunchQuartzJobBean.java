package com.vteba.batch.quartz;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Sprin Batch和Quartz的Job的桥接Bean。
 * 
 * @author yinlei
 * @date 2016年3月12日 下午5:08:41
 */
public class LaunchQuartzJobBean extends QuartzJobBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(LaunchQuartzJobBean.class);

	/**
	 * Special key in job data map for the name of a job to run.
	 */
	static final String JOB_NAME = "jobName";

	private JobLocator jobLocator;
	private JobLauncher jobLauncher;

	/**
	 * Public setter for the {@link JobLocator}.
	 * 
	 * @param jobLocator
	 *            the {@link JobLocator} to set
	 */
	public void setJobLocator(JobLocator jobLocator) {
		this.jobLocator = jobLocator;
	}

	/**
	 * Public setter for the {@link JobLauncher}.
	 * 
	 * @param jobLauncher
	 *            the {@link JobLauncher} to set
	 */
	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) {
		Map<String, Object> jobDataMap = context.getMergedJobDataMap();
		String jobName = (String) jobDataMap.get(JOB_NAME);
		LOGGER.info("Quartz trigger firing with Spring Batch jobName=[{}]", jobName);
		JobParameters jobParameters = getJobParametersFromJobMap(jobDataMap);
		try {
			jobLauncher.run(jobLocator.getJob(jobName), jobParameters);
		} catch (JobExecutionException e) {
			LOGGER.error("Could not execute job, msg=[{}].", e);
		}
	}

	/*
	 * Copy parameters that are of the correct type over to {@link
	 * JobParameters}, ignoring jobName.
	 * 
	 * @return a {@link JobParameters} instance
	 */
	private JobParameters getJobParametersFromJobMap(Map<String, Object> jobDataMap) {

		JobParametersBuilder builder = new JobParametersBuilder();

		for (Entry<String, Object> entry : jobDataMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof String && !key.equals(JOB_NAME)) {
				builder.addString(key, (String) value);
			} else if (value instanceof Float || value instanceof Double) {
				builder.addDouble(key, ((Number) value).doubleValue());
			} else if (value instanceof Integer || value instanceof Long) {
				builder.addLong(key, ((Number) value).longValue());
			} else if (value instanceof Date) {
				builder.addDate(key, (Date) value);
			} else {
				LOGGER.debug("JobDataMap contains values which are not job parameters (ignoring).");
			}
		}

		return builder.toJobParameters();
	}
}
