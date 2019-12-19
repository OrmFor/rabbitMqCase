package com.rabbitconsumer.demo.pojo.entity;

import cc.s2m.web.utils.webUtils.pojo.BaseModelBean;

public class RabbitconfigEntity extends BaseModelBean {
    private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String rabbitmqHost;
	private String rabbitmqPort;
	private String rabbitmqUserName;
	private String rabbitmqPassword;
	private String yqsHost;
	private String yqsUserId;
	private String appId;
	private String yqsSecret;
	private String rabbitmqQueueName;
	private String rabbitmqVirtualHost;
	private Integer status;
	
    public Integer getId() {
        return id;
    }
	public RabbitconfigEntity setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getRabbitmqHost() {
        return rabbitmqHost;
    }
	public RabbitconfigEntity setRabbitmqHost(String rabbitmqHost) {
        this.rabbitmqHost = rabbitmqHost;
        return this;
    }
    public String getRabbitmqPort() {
        return rabbitmqPort;
    }
	public RabbitconfigEntity setRabbitmqPort(String rabbitmqPort) {
        this.rabbitmqPort = rabbitmqPort;
        return this;
    }
    public String getRabbitmqUserName() {
        return rabbitmqUserName;
    }
	public RabbitconfigEntity setRabbitmqUserName(String rabbitmqUserName) {
        this.rabbitmqUserName = rabbitmqUserName;
        return this;
    }
    public String getRabbitmqPassword() {
        return rabbitmqPassword;
    }
	public RabbitconfigEntity setRabbitmqPassword(String rabbitmqPassword) {
        this.rabbitmqPassword = rabbitmqPassword;
        return this;
    }
    public String getYqsHost() {
        return yqsHost;
    }
	public RabbitconfigEntity setYqsHost(String yqsHost) {
        this.yqsHost = yqsHost;
        return this;
    }
    public String getYqsUserId() {
        return yqsUserId;
    }
	public RabbitconfigEntity setYqsUserId(String yqsUserId) {
        this.yqsUserId = yqsUserId;
        return this;
    }
    public String getAppId() {
        return appId;
    }
	public RabbitconfigEntity setAppId(String appId) {
        this.appId = appId;
        return this;
    }
    public String getYqsSecret() {
        return yqsSecret;
    }
	public RabbitconfigEntity setYqsSecret(String yqsSecret) {
        this.yqsSecret = yqsSecret;
        return this;
    }
    public String getRabbitmqQueueName() {
        return rabbitmqQueueName;
    }
	public RabbitconfigEntity setRabbitmqQueueName(String rabbitmqQueueName) {
        this.rabbitmqQueueName = rabbitmqQueueName;
        return this;
    }
    public String getRabbitmqVirtualHost() {
        return rabbitmqVirtualHost;
    }
	public RabbitconfigEntity setRabbitmqVirtualHost(String rabbitmqVirtualHost) {
        this.rabbitmqVirtualHost = rabbitmqVirtualHost;
        return this;
    }
    public Integer getStatus() {
        return status;
    }
	public RabbitconfigEntity setStatus(Integer status) {
        this.status = status;
        return this;
    }
}