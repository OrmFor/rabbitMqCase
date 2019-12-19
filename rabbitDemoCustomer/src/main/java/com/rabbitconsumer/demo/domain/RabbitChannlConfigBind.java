package com.rabbitconsumer.demo.domain;


import com.rabbitmq.client.Channel;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class RabbitChannlConfigBind {

    private String queueName;

    private Channel channel;

    private String appId;

    private String userId;
}
