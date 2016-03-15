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
 * Spring上下文启动成功监听器。可以在这里做一些动态添加Bean的操作等。
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
		LOGGER.info("Spring上下文刷新成功. EventName=[{}].", displayName);
		
		String namespace = context.getNamespace();
		// spring root 名空间是null，其他为非null，例如spring-mvc
		if (namespace != null || !"Root WebApplicationContext".equals(displayName)) {
			// 只将Bean注册到spring root上下文，不注册到其他上下文中，如spring mvc的上下文，所以返回，如果需要也可以注入的
			return;
		}
		DefaultListableBeanFactory factory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
		// 手动生成的Bean，手动注册到spring 上下文
		User user = new User();
		user.setName("yinlei");
		factory.registerSingleton("user", user);
		
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(User.class);
		builder.addPropertyValue("name", "yinlei2");
		
		BeanDefinition definition = builder.getBeanDefinition();
		definition.setAttribute("id", "user1");
		
		factory.registerBeanDefinition("user2", definition);
		
//		User testUser = (User) factory.getBean("user");
//		System.out.println(testUser);
//		
//		testUser = (User) factory.getBean("user2");
//		System.out.println(testUser);
//		
//		testUser = (User) factory.getBean("user", User.class);
//		System.out.println(testUser);
//		
//		testUser = (User) factory.getBean("user2", User.class);
//		System.out.println(testUser);
		//System.out.println(factory.getBean(User.class));
	}

}
