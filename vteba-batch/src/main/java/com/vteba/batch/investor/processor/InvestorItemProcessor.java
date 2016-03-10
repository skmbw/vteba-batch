package com.vteba.batch.investor.processor;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.vteba.batch.investor.model.Investor;

public class InvestorItemProcessor implements ItemProcessor<Investor, Investor> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InvestorItemProcessor.class);
	
	@Override
	public Investor process(Investor item) throws Exception {
		String name = item.getName();
		LOGGER.info("开始处理investor=[{}]", name);
		try {
			item.setCreateDate(new Date());
		} catch (Exception e) {
			LOGGER.info("处理investor=[{}]失败, msg=[{}]", name, e);
		}
		if (item.getId() == 5) {
			throw new RuntimeException("Test Transaction.");
		}
		LOGGER.info("处理investor=[{}]成功。", name);
		return item;
	}

}
