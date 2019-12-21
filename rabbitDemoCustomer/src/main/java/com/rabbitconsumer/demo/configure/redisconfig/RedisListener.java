package com.rabbitconsumer.demo.configure.redisconfig;

import com.google.common.collect.Lists;
import com.rabbitconsumer.demo.configure.RabbitMqGroup;
import com.rabbitconsumer.demo.controller.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.List;
import java.util.concurrent.CountDownLatch;


/**
* @Author wwy
* @Description redis监听器
* @Date 22:07 2019/12/16
* @Param
* @return
**/
@Configuration
public class RedisListener {

    static final List<PatternTopic> tops = Lists.newArrayList(
            new PatternTopic("addUser"),
            new PatternTopic("startUser"),
            new PatternTopic("stopUser")
    );

    @Autowired
    private RabbitMqGroup rabbitMqGroup;


    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapterAdd,
                                            MessageListenerAdapter listenerAdapterStart,
                                            MessageListenerAdapter listenerAdapterStop) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapterAdd,  new PatternTopic("addUser"+rabbitMqGroup.getGroup()));
        container.addMessageListener(listenerAdapterStart,  new PatternTopic("startUser"+rabbitMqGroup.getGroup()));
        container.addMessageListener(listenerAdapterStop,  new PatternTopic("stopUser"+rabbitMqGroup.getGroup()));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapterAdd(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage4Add");
    }

    @Bean
    MessageListenerAdapter listenerAdapterStart(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage4Start");
    }

    @Bean
    MessageListenerAdapter listenerAdapterStop(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage4Stop");
    }


    @Bean
    Receiver receiver(CountDownLatch latch)  {
        return new Receiver(latch);
    }

    @Bean
    CountDownLatch latch() {
        return new CountDownLatch(1);
    }

    @Bean
    StringRedisTemplate template(JedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
}
