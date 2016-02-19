package com.vteba.batch.user;

import static org.springframework.util.Assert.notNull;
import static org.springframework.util.ClassUtils.getShortName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.AbstractPagingItemReader;

/**
 * {@code org.springframework.batch.item.ItemReader} for reading database
 * records using MyBatis in a paging fashion.
 * <p>
 * Provided to facilitate the migration from Spring-Batch iBATIS 2 page item
 * readers to MyBatis 3.
 *
 * @since 1.1.0
 * @version $Id$
 */
public class MyBatisPagingItemReader<T> extends AbstractPagingItemReader<T> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisPagingItemReader.class);
	
	private String queryId;

	private SqlSessionFactory sqlSessionFactory;

	private SqlSessionTemplate sqlSessionTemplate;

	private Map<String, Object> parameterValues;

	public MyBatisPagingItemReader() {
		setName(getShortName(MyBatisPagingItemReader.class));
	}

	/**
	 * Public setter for {@link SqlSessionFactory} for injection purposes.
	 *
	 * @param SqlSessionFactory
	 *            sqlSessionFactory
	 */
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	/**
	 * Public setter for the statement id identifying the statement in the
	 * SqlMap configuration file.
	 *
	 * @param queryId
	 *            the id for the statement
	 */
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	/**
	 * The parameter values to be used for the query execution.
	 *
	 * @param parameterValues
	 *            the values keyed by the parameter named used in the query
	 *            string.
	 */
	public void setParameterValues(Map<String, Object> parameterValues) {
		this.parameterValues = parameterValues;
	}

	/**
	 * Check mandatory properties.
	 * 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		notNull(sqlSessionFactory);
		sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.SIMPLE);
		notNull(queryId);
	}

	@Override
	protected void doReadPage() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (parameterValues != null) {
			parameters.putAll(parameterValues);
		}
		int page = getPage();
		int pageSize = getPageSize();
		parameters.put("_page", page);
		parameters.put("_pagesize", pageSize);
		parameters.put("pageSize", pageSize);
		int start = page * pageSize;
		parameters.put("start", start);
		parameters.put("_skiprows", start);
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("开始读取第[{}]页，pageSize=[{}].", page, pageSize);
		}
		List<T> list = sqlSessionTemplate.<T> selectList(queryId, parameters);
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("第[{}]页，读取到[{}]条数据.", page, list.size());
		}
		results.addAll(list);
	}

	@Override
	protected void doJumpToPage(int itemIndex) {
		LOGGER.info("索引是[{}]", itemIndex);
	}

}
