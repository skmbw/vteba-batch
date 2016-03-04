package com.vteba.dubbo.logger;

import java.io.File;

import org.apache.logging.log4j.LogManager;

import com.alibaba.dubbo.common.logger.Level;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerAdapter;

/**
 * 给予Log4j2实现的dubbo的Logger适配器。
 * 
 * @author yinlei
 * @date 2016年3月4日 下午2:21:27
 */
public class Log4j2LoggerAdapter implements LoggerAdapter {

	private File file;

	public Log4j2LoggerAdapter() {
//		try {
//			org.apache.logging.log4j.Logger logger = LogManager.getRootLogger();
//			
//			if (logger != null) {
//				Enumeration<Appender> appenders = logger.getAllAppenders();
//				if (appenders != null) {
//					while (appenders.hasMoreElements()) {
//						Appender appender = appenders.nextElement();
//						if (appender instanceof FileAppender) {
//							FileAppender fileAppender = (FileAppender) appender;
//							String filename = fileAppender.getFileName();
//							file = new File(filename);
//							break;
//						}
//					}
//				}
//			}
//		} catch (Throwable t) {
//			
//		}
	}

	public Logger getLogger(Class<?> key) {
		return new Log4j2Logger(LogManager.getLogger(key));
	}

	public Logger getLogger(String key) {
		return new Log4j2Logger(LogManager.getLogger(key));
	}

	public void setLevel(Level level) {
		// TODO
	}

	public Level getLevel() {
		return fromLog4j2Level(LogManager.getRootLogger().getLevel());
	}

	public File getFile() {
		return file;
	}

//	private static org.apache.logging.log4j.Level toLog4j2Level(Level level) {
//		if (level == Level.ALL)
//			return org.apache.logging.log4j.Level.ALL;
//		if (level == Level.TRACE)
//			return org.apache.logging.log4j.Level.TRACE;
//		if (level == Level.DEBUG)
//			return org.apache.logging.log4j.Level.DEBUG;
//		if (level == Level.INFO)
//			return org.apache.logging.log4j.Level.INFO;
//		if (level == Level.WARN)
//			return org.apache.logging.log4j.Level.WARN;
//		if (level == Level.ERROR)
//			return org.apache.logging.log4j.Level.ERROR;
//		
//		return org.apache.logging.log4j.Level.OFF;
//	}

	private static Level fromLog4j2Level(org.apache.logging.log4j.Level level) {
		if (level == org.apache.logging.log4j.Level.ALL)
			return Level.ALL;
		if (level == org.apache.logging.log4j.Level.TRACE)
			return Level.TRACE;
		if (level == org.apache.logging.log4j.Level.DEBUG)
			return Level.DEBUG;
		if (level == org.apache.logging.log4j.Level.INFO)
			return Level.INFO;
		if (level == org.apache.logging.log4j.Level.WARN)
			return Level.WARN;
		if (level == org.apache.logging.log4j.Level.ERROR)
			return Level.ERROR;
		
		return Level.OFF;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
