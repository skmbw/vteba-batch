package com.vteba.batch.user.skip;

import java.util.Map;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.beans.factory.InitializingBean;

import com.vteba.utils.common.Assert;

//@Named
public class UserSkipPolicy implements SkipPolicy, InitializingBean {
	private Map<Class<?>, Integer> throwableSkipMap;
	
	@Override
	public boolean shouldSkip(Throwable t, int skipCount) throws SkipLimitExceededException {
		Integer count = throwableSkipMap.get(t.getClass());
		if (count == null) { // 不是预配置的 跳过异常，不能跳过
			return false;
		} else {
			if (skipCount <= count) { // 是配置的跳过的异常，数量还没有达到，跳过
				return true;
			}
		}
		return false;
	}

	public void setThrowableSkipMap(Map<Class<?>, Integer> throwableSkipMap) {
		this.throwableSkipMap = throwableSkipMap;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(throwableSkipMap, "请配置异常策略组合property[throwableSkipMap].");
	}

}
