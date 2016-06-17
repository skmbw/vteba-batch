package com.vteba.batch.send.model;

import com.vteba.annotation.Kryo;

@Kryo
public class Send {
	
	private Integer age;
	
    /**
     * order by 排序语句
     * 对应数据库表字段 send
     */
    private String orderBy;

    /**
     * 分页开始记录
     * 对应数据库表字段 send
     */
    private Integer start;

    /**
     * 分页大小
     * 对应数据库表字段 send
     */
    private int pageSize = 10;

    /**
     * 是否去重
     * 对应数据库表字段 send
     */
    private boolean distinct;

    /**
     * 对应数据库表字段 send.id
     */
    private Integer id;

    /**
     * 对应数据库表字段 send.name
     */
    private String name;

    /**
     * 设置 order by 排序语句
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * 获得 order by 排序语句
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * 设置 start，分页开始记录
     */
    public void setStart(Integer start) {
        this.start = start;
    }

    /**
     * 获得 start，分页开始记录
     */
    public Integer getStart() {
        return start;
    }

    /**
     * 设置 pageSize，分页大小
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获得 pageSize，分页大小
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置 distinct，是否去重
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * 获得 distinct，是否去重
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * 获得字段 send.id 的值
     *
     * @return the value of send.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置字段 send.id 的值
     *
     * @param id the value for send.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获得字段 send.name 的值
     *
     * @return the value of send.name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置字段 send.name 的值
     *
     * @param name the value for send.name
     */
    public void setName(String name) {
        this.name = name;
    }

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}