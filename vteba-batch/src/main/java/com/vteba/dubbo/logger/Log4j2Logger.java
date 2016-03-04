package com.vteba.dubbo.logger;

import org.apache.logging.log4j.Level;

import com.alibaba.dubbo.common.logger.Logger;

/**
 * 基于Log4j2实现的dubbo的Logger。
 * 
 * @author yinlei
 * @date 2016年3月4日 下午2:20:14
 */
public class Log4j2Logger implements Logger {

	private final org.apache.logging.log4j.Logger logger;

	public Log4j2Logger(org.apache.logging.log4j.Logger logger) {
		this.logger = logger;
	}

	public void trace(String msg) {
		logger.log(Level.TRACE, msg);
	}

	public void trace(Throwable e) {
		logger.log(Level.TRACE, e == null ? null : e.getMessage(), e);
	}

	public void trace(String msg, Throwable e) {
		logger.log(Level.TRACE, msg, e);
	}

	public void debug(String msg) {
		logger.log(Level.DEBUG, msg);
	}

	public void debug(Throwable e) {
		logger.log(Level.DEBUG, e == null ? null : e.getMessage(), e);
	}

	public void debug(String msg, Throwable e) {
		logger.log(Level.DEBUG, msg, e);
	}

	public void info(String msg) {
		logger.log(Level.INFO, msg);
	}

	public void info(Throwable e) {
		logger.log(Level.INFO, e == null ? null : e.getMessage(), e);
	}

	public void info(String msg, Throwable e) {
		logger.log(Level.INFO, msg, e);
	}

	public void warn(String msg) {
		logger.log(Level.WARN, msg);
	}

	public void warn(Throwable e) {
		logger.log(Level.WARN, e == null ? null : e.getMessage(), e);
	}

	public void warn(String msg, Throwable e) {
		logger.log(Level.WARN, msg, e);
	}

	public void error(String msg) {
		logger.log(Level.ERROR, msg);
	}

	public void error(Throwable e) {
		logger.log(Level.ERROR, e == null ? null : e.getMessage(), e);
	}

	public void error(String msg, Throwable e) {
		logger.log(Level.ERROR, msg, e);
	}

	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

}
