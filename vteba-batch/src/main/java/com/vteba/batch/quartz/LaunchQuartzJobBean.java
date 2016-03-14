package com.vteba.batch.quartz;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.JobExecutionContext;
import org.quartz.impl.JobDetailImpl;
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
	private static final String JOB_NAME = "jobName";
	
	private JobLocator jobLocator;
	private JobLauncher jobLauncher;
	private String dateFormat;
	private Map<String, String> jobParameters;
	private String jobName;

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
		if (jobName == null) {
			JobDetailImpl jobDetailImpl = (JobDetailImpl) context.getJobDetail();
			// 不配置默认是Bean的名字，按照规范，名字是JobName + Detail
			jobName = jobDetailImpl.getName();
			jobName = jobName.substring(0, jobName.length() - 6);
		}
		//String jobName = (String) jobDataMap.get(JOB_NAME);
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Quartz trigger firing with Spring Batch jobName=[{}]", jobName);
		}
		JobParameters jobParameters = getJobParametersFromJobMap(jobDataMap);
		try {
			jobLauncher.run(jobLocator.getJob(jobName), jobParameters);
		} catch (JobExecutionException e) {
			LOGGER.error("Could not execute job, msg=[{}].", e);
		}
	}

	/**
	 * Copy parameters that are of the correct type over to {@link
	 * JobParameters}, ignoring jobName.<br>
	 * 只能接受String Long Double Date四种数据类型。
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

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Map<String, String> getJobParameters() {
		return jobParameters;
	}

	public void setJobParameters(Map<String, String> jobParameters) {
		this.jobParameters = jobParameters;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
}
