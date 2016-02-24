package com.vteba.batch;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.vteba.batch.user.model.User;

/**
 * Spring上下文启动成功监听器
 * @author yinlei
 * @date 2016-2-24
 */
@Named
public class DefaultApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultApplicationListener.class);
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		XmlWebApplicationContext context = (XmlWebApplicationContext) event.getSource();
		
		String displayName = context.getDisplayName();
		LOGGER.info("事件来源[{}].", displayName);
		
		String namespace = context.getNamespace();
		// spring root 名空间是null，其他为非null，例如spring-mvc
		if (namespace != null || !"Root WebApplicationContext".equals(displayName)) {
			return;
		}
		DefaultListableBeanFactory factory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
		
		User user = new User();
		user.setName("yinlei");
		factory.registerSingleton("user", user);
		
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(User.class);
		builder.addPropertyValue("name", "yinlei2");
		
		BeanDefinition definition = builder.getBeanDefinition();
		definition.setAttribute("id", "user1");
		
		factory.registerBeanDefinition("user2", definition);
		
		User testUser = (User) factory.getBean("user");
		System.out.println(testUser);
		
		testUser = (User) factory.getBean("user2");
		System.out.println(testUser);
		
		testUser = (User) factory.getBean("user", User.class);
		System.out.println(testUser);
		
		testUser = (User) factory.getBean("user2", User.class);
		System.out.println(testUser);
		//System.out.println(factory.getBean(User.class));
	}

}
