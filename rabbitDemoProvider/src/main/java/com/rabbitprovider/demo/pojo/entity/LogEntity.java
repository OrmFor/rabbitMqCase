package com.rabbitprovider.demo.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;
import org.springframework.format.annotation.DateTimeFormat;

public class LogEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String queueName;
	private String remark;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date createTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date updateTime;
	private String type;
	
    public Integer getId() {
        return id;
    }
	public LogEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getQueueName() {
        return queueName;
    }
	public LogEntity setQueueName(String queueName) {
        this.queueName = queueName;
        return this;
    }
    public String getRemark() {
        return remark;
    }
	public LogEntity setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public java.util.Date getCreateTime() {
        return createTime;
    }
	public LogEntity setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public java.util.Date getUpdateTime() {
        return updateTime;
    }
	public LogEntity setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    public String getType() {
        return type;
    }
	public LogEntity setType(String type) {
        this.type = type;
        return this;
    }
}