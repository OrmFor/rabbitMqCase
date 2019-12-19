package com.rabbitconsumer.demo.controller;

import com.rabbitconsumer.demo.domain.ChannelBean;
import com.rabbitconsumer.demo.domain.RabbitChannlConfigBind;
import com.rabbitconsumer.demo.pojo.Rabbitconfig;
import com.rabbitconsumer.demo.service.IRabbitconfig;
import com.rabbitconsumer.demo.util.SpringContextUtils;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.ConcurrentHashMap;


/**
* @Author wwy
* @Description redis监听到添加、启动消息后，启动该线程
* @Date 11:16 2019/12/16
* @Param
* @return
**/
public class ThreadReceiveProcess extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadReceiveProcess.class);


    @Autowired
    private CustomerListener customerListener;

    private String messageQueue;

    private ConnectionFactory connectionFactory;

    public ThreadReceiveProcess(String message,ConnectionFactory connectionFactory,ConcurrentHashMap<ChannelBean,Channel> channelConcurrentHashMap){
        this.messageQueue = message;
        this.connectionFactory = connectionFactory;
        this.channelConcurrentHashMap = channelConcurrentHashMap;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void setMessage(String message) {
        this.messageQueue = message;
    }

    private ConcurrentHashMap<ChannelBean,Channel> channelConcurrentHashMap ;

    public void setChannelConcurrentHashMap(ConcurrentHashMap<ChannelBean, Channel> channelConcurrentHashMap) {
        this.channelConcurrentHashMap = channelConcurrentHashMap;
    }

    @Override
    public void run() {
        LOGGER.info("====================={}信道重启中===========================",messageQueue);

        Rabbitconfig rabbitconfig = new Rabbitconfig();
        rabbitconfig.setRabbitmqQueueName(messageQueue);

        IRabbitconfig rabbitconfigService = (IRabbitconfig) SpringContextUtils.getBeanByClass(IRabbitconfig.class);
        Rabbitconfig bean = rabbitconfigService.getByCondition(rabbitconfig);

        RabbitChannlConfigBind bind = RabbitChannlConfigBind.builder().appId(bean.getAppId())
                .userId(bean.getYqsUserId())
                .build();

       // Channel channel = channelConcurrentHashMap.get(messageQueue);
        Channel channel = connectionFactory.createConnection().createChannel(false);

        //声明要关注的队列
        try {
             if(channel != null) {
                 channel.queueDeclare(messageQueue, true,
                         false, false, null);

                 bind.setQueueName(messageQueue);
                 bind.setChannel(channel);
                 //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
                 // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
                 Consumer consumer = new DefaultConsumer(channel) {
                     @Override
                     public void handleDelivery(String consumerTag, Envelope envelope,
                                                AMQP.BasicProperties properties, byte[] body)
                             throws IOException {
                         LOGGER.info("=======================信道重启后绑定开始===============");
                         String message = new String(body, "UTF-8");
                         if(message.equals("Error")){
                             throw new RuntimeException("Error");
                         }
                         LOGGER.info(MessageFormat.format("Customer = {0}, Received ={1}", messageQueue, message));
                         LOGGER.info(bind.toString());
                         LOGGER.info("=======================信道重启后绑定结束===============");

                     }
                 };
                 //自动回复队列应答 -- RabbitMQ中的消息确认机制
                 String tag =channel.basicConsume(messageQueue, true, consumer);
                 ChannelBean bean1 = new ChannelBean();
                 bean1.setTag(tag);
                 bean1.setQueueName(messageQueue);
                 channelConcurrentHashMap.put(bean1,channel);

                 LOGGER.info("====================={}信道重启完毕===========================", messageQueue);
             }else{
                  channel = connectionFactory.createConnection().createChannel(false);
                  channel.queueDeclare(messageQueue, true,
                         false, false, null);

                 Consumer consumer = new DefaultConsumer(channel) {
                     @Override
                     public void handleDelivery(String consumerTag, Envelope envelope,
                                                AMQP.BasicProperties properties, byte[] body)
                             throws IOException {
                         String message = new String(body, "UTF-8");
                         LOGGER.info(MessageFormat.format("Customer = {0}, Received ={1}", messageQueue, message));
                     }
                 };
                 //自动回复队列应答 -- RabbitMQ中的消息确认机制
                 String tag = channel.basicConsume(messageQueue, true, consumer);
                 ChannelBean bean1 = new ChannelBean();
                 bean1.setTag(tag);
                 bean1.setQueueName(messageQueue);
                 channelConcurrentHashMap.put(bean1,channel);

                 LOGGER.info("====================={}信道重启完毕===========================", messageQueue);

             }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
