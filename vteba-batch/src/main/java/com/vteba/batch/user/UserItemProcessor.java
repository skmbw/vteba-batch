package com.vteba.batch.user;

import java.util.Date;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.vteba.batch.user.model.User;
import com.vteba.common.exception.BasicException;

@Named
public class UserItemProcessor implements ItemProcessor<User, User> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserItemProcessor.class);
	
	@Override
	public User process(User item) throws Exception {
		String name = item.getName();
		LOGGER.info("开始处理user=[{}]", name);
		try {
			item.setUpdateDate(new Date());
		} catch (Exception e) {
			LOGGER.info("处理user=[{}]失败, msg=[{}]", name, e);
		}
//		if (item.getId() == 5) {
//			throw new ServiceException("Test Transaction.");
//		}
		
		if (item.getId() == 5) {
			LOGGER.error("抛出跳过异常。");
			//throw new BasicException("抛出的跳过异常");
		}
		
		LOGGER.info("处理user=[{}]成功。", name);
		return item;
	}

}
