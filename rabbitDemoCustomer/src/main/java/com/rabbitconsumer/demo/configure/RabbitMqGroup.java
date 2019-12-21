package com.rabbitconsumer.demo.configure;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@ConfigurationProperties(prefix = "rabbit")
@Data
public class RabbitMqGroup {

    private int group;

}
