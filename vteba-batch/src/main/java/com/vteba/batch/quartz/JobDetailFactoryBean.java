package com.vteba.batch.quartz;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.JobDetailImpl;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * A Spring {@link FactoryBean} for creating a Quartz {@link org.quartz.JobDetail}
 * instance, supporting bean-style usage for JobDetail configuration.
 *
 * <p>{@code JobDetail(Impl)} itself is already a JavaBean but lacks
 * sensible defaults. This class uses the Spring bean name as job name,
 * and the Quartz default group ("DEFAULT") as job group if not specified.
 *
 * @author Juergen Hoeller
 * @since 3.1
 * @see #setName
 * @see #setGroup
 * @see org.springframework.beans.factory.BeanNameAware
 * @see org.quartz.Scheduler#DEFAULT_GROUP
 */
public class JobDetailFactoryBean
		implements FactoryBean<JobDetail>, BeanNameAware, ApplicationContextAware, InitializingBean {

	private String jobName; // 这个是spring batch的job name

	private String name; // 这个是quartz的job name
	
	private String group;

	private Class<? extends Job> jobClass;

	private JobDataMap jobDataMap = new JobDataMap();

	private boolean durability = false;

	private boolean requestsRecovery = false;

	private String description;

	private String beanName;

	private ApplicationContext applicationContext;

	private String applicationContextJobDataKey;

	private JobDetail jobDetail;
	
	// job中需要，找不到不报错，可能在jobDataMap中设置了
	@Autowired(required = false)
	private JobLauncher jobLauncher;
	@Autowired(required = false)
	private JobLocator jobLocator;

	private String dateFormat;
	private Map<String, String> jobParameters;
	
	/**
	 * Specify the job's name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Specify the job's group.
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * Specify the job's implementation class.
	 */
	public void setJobClass(Class<? extends Job> jobClass) {
		this.jobClass = jobClass;
	}

	/**
	 * Set the job's JobDataMap.
	 * @see #setJobDataAsMap
	 */
	public void setJobDataMap(JobDataMap jobDataMap) {
		this.jobDataMap = jobDataMap;
	}

	/**
	 * Return the job's JobDataMap.
	 */
	public JobDataMap getJobDataMap() {
		return this.jobDataMap;
	}

	/**
	 * Register objects in the JobDataMap via a given Map.
	 * <p>These objects will be available to this Job only,
	 * in contrast to objects in the SchedulerContext.
	 * <p>Note: When using persistent Jobs whose JobDetail will be kept in the
	 * database, do not put Spring-managed beans or an ApplicationContext
	 * reference into the JobDataMap but rather into the SchedulerContext.
	 * @param jobDataAsMap Map with String keys and any objects as values
	 * (for example Spring-managed beans)
	 * @see org.springframework.scheduling.quartz.SchedulerFactoryBean#setSchedulerContextAsMap
	 */
	public void setJobDataAsMap(Map<String, ?> jobDataAsMap) {
		getJobDataMap().putAll(jobDataAsMap);
	}

	/**
	 * Specify the job's durability, i.e. whether it should remain stored
	 * in the job store even if no triggers point to it anymore.
	 */
	public void setDurability(boolean durability) {
		this.durability = durability;
	}

	/**
	 * Set the recovery flag for this job, i.e. whether or not the job should
	 * get re-executed if a 'recovery' or 'fail-over' situation is encountered.
	 */
	public void setRequestsRecovery(boolean requestsRecovery) {
		this.requestsRecovery = requestsRecovery;
	}

	/**
	 * Set a textual description for this job.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 * Set the key of an ApplicationContext reference to expose in the JobDataMap,
	 * for example "applicationContext". Default is none.
	 * Only applicable when running in a Spring ApplicationContext.
	 * <p>In case of a QuartzJobBean, the reference will be applied to the Job
	 * instance as bean property. An "applicationContext" attribute will correspond
	 * to a "setApplicationContext" method in that scenario.
	 * <p>Note that BeanFactory callback interfaces like ApplicationContextAware
	 * are not automatically applied to Quartz Job instances, because Quartz
	 * itself is responsible for the lifecycle of its Jobs.
	 * <p><b>Note: When using persistent job stores where JobDetail contents will
	 * be kept in the database, do not put an ApplicationContext reference into
	 * the JobDataMap but rather into the SchedulerContext.</b>
	 * @see org.springframework.scheduling.quartz.SchedulerFactoryBean#setApplicationContextSchedulerContextKey
	 * @see org.springframework.context.ApplicationContext
	 */
	public void setApplicationContextJobDataKey(String applicationContextJobDataKey) {
		this.applicationContextJobDataKey = applicationContextJobDataKey;
	}


	@Override
	public void afterPropertiesSet() {
		if (this.name == null) {
			this.name = this.beanName;
		}
		if (this.group == null) {
			this.group = Scheduler.DEFAULT_GROUP;
		}
		if (this.applicationContextJobDataKey != null) {
			if (this.applicationContext == null) {
				throw new IllegalStateException(
					"JobDetailBean needs to be set up in an ApplicationContext " +
					"to be able to handle an 'applicationContextJobDataKey'");
			}
			getJobDataMap().put(this.applicationContextJobDataKey, this.applicationContext);
		}

		JobDetailImpl jdi = new JobDetailImpl();
		jdi.setName(this.name);
		jdi.setGroup(this.group);
		if (this.jobClass == null) {
			jdi.setJobClass(LaunchQuartzJobBean.class);
		} else {
			jdi.setJobClass(this.jobClass);
		}
		
		// jobDataMap中的参数会自动注入到Job中
		// 这些参数（以下5个）其实在jobDataMap中设置也是可以的
		if (jobLauncher != null) {
			jobDataMap.put("jobLauncher", jobLauncher);
		}
		if (jobLocator != null) {
			jobDataMap.put("jobLocator", jobLocator);
		}
		if (dateFormat != null) {
			jobDataMap.put("dateFormat", dateFormat);
		}
		if (jobParameters != null) {
			jobDataMap.put("jobParameters", jobParameters);
		}
		if (jobName == null) { // 没有明确配置spring batch的job name
			// 这个可能是配置的job name或者是bean的名字
			String jobName = name.substring(0, name.length() - 6);
			jobDataMap.put("jobName", jobName);
		} else {
			jobDataMap.put("jobName", this.jobName);
		}
		
		jdi.setJobDataMap(this.jobDataMap);
		jdi.setDurability(this.durability);
		jdi.setRequestsRecovery(this.requestsRecovery);
		jdi.setDescription(this.description);
		this.jobDetail = jdi;
	}


	@Override
	public JobDetail getObject() {
		return this.jobDetail;
	}

	@Override
	public Class<?> getObjectType() {
		return JobDetail.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}

	public void setJobLocator(JobLocator jobLocator) {
		this.jobLocator = jobLocator;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public void setJobParameters(Map<String, String> jobParameters) {
		this.jobParameters = jobParameters;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

}
