//package com.rabbitprovider.demo;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.listener.PatternTopic;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
//
//import java.util.concurrent.CountDownLatch;
//
//@SpringBootApplication
//public class Application {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
//
//    @Bean
//    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
//                                            MessageListenerAdapter listenerAdapter) {
//
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));
//
//        return container;
//    }
//
//    @Bean
//    MessageListenerAdapter listenerAdapter(Receiver1 receiver1) {
//        return new MessageListenerAdapter(receiver1, "receiveMessage");
//    }
//
//    @Bean
//    Receiver1 receiver(CountDownLatch latch) {
//        return new Receiver1(latch);
//    }
//
//    @Bean
//    CountDownLatch latch() {
//        return new CountDownLatch(1);
//    }
//
//    @Bean
//    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
//        return new StringRedisTemplate(connectionFactory);
//    }
//
//    public static void main(String[] args) throws InterruptedException {
//
//        ApplicationContext ctx = SpringApplication.run(Application.class, args);
//
//        StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
//        CountDownLatch latch = ctx.getBean(CountDownLatch.class);
//
//        LOGGER.info("Sending message...");
//        template.convertAndSend("chat", "Hello from Redis!");
//
//        latch.await();
//
//        System.exit(0);
//    }
//}