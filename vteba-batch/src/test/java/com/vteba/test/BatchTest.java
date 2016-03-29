package com.vteba.test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vteba.batch.user.model.User;
import com.vteba.batch.user.service.spi.UserService;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:spring/application-context.xml")
public class BatchTest {

	@Inject
	private UserService userServiceImpl;
	
	@Test
	public void test() {
		for (int i = 0; i < 500; i++) {
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			User user = new User();
			user.setAge(22 + i);
			user.setUpdateDate(new Date());
			user.setName("yinlei44" + i);
			userServiceImpl.save(user);
		}
	}
}
