package com.rabbitprovider.demo.configure;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rabbit")
@Data
public class RabbitMqGroup {

    private int group;

}
