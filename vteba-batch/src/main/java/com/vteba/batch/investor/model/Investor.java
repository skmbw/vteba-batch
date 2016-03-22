package com.vteba.batch.investor.model;

import java.util.Date;

import com.vteba.annotation.KryoBean;

@KryoBean
public class Investor {
    /**
     * order by 排序语句
     * 对应数据库表字段 investor
     */
    private String orderBy;

    /**
     * 分页开始记录
     * 对应数据库表字段 investor
     */
    private Integer start;

    /**
     * 分页大小
     * 对应数据库表字段 investor
     */
    private int pageSize = 10;

    /**
     * 是否去重
     * 对应数据库表字段 investor
     */
    private boolean distinct;

    /**
     * 对应数据库表字段 investor.id
     */
    private Integer id;

    /**
     * 对应数据库表字段 investor.name
     */
    private String name;

    /**
     * 对应数据库表字段 investor.age
     */
    private Integer age;

    /**
     * 对应数据库表字段 investor.create_date
     */
    private Date createDate;

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
     * 获得字段 investor.id 的值
     *
     * @return the value of investor.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置字段 investor.id 的值
     *
     * @param id the value for investor.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获得字段 investor.name 的值
     *
     * @return the value of investor.name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置字段 investor.name 的值
     *
     * @param name the value for investor.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获得字段 investor.age 的值
     *
     * @return the value of investor.age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置字段 investor.age 的值
     *
     * @param age the value for investor.age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获得字段 investor.create_date 的值
     *
     * @return the value of investor.create_date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置字段 investor.create_date 的值
     *
     * @param createDate the value for investor.create_date
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}