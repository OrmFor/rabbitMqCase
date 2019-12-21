package com.rabbitconsumer.demo.controller;

import com.rabbitconsumer.demo.configure.RabbitMqGroup;
import com.rabbitconsumer.demo.domain.ChannelBean;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @Author wwy
 * @Description 监听信道是否有异常，存在异常重试连接
 * @Date 11:18 2019/12/16
 * @Param
 * @return
 **/
@Component
@EnableScheduling
public class ChannelMapTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelMapTask.class);


    @Autowired
    private CustomerListener customerListener;

    @Autowired
    private RabbitMqGroup rabbitMqGroup;



    @Scheduled(cron = "0 */30 * * * ?")
    public void channelMapTask() {
        System.out.println(rabbitMqGroup.getGroup());
        ConcurrentHashMap<ChannelBean, Channel> channels = customerListener.getConcurrentHashMap();
        LOGGER.info("ChannelMapTask数量:" + channels.size());

        for (Map.Entry<ChannelBean, Channel> entry : channels.entrySet()) {
            Channel channel = entry.getValue();
            try {
                ChannelBean bean = entry.getKey();
                LOGGER.info("ChannelMapTask" + bean.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
