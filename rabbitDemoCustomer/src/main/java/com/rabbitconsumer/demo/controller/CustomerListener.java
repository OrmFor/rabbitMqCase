package com.rabbitconsumer.demo.controller;

import com.google.common.collect.Lists;
import com.rabbitconsumer.demo.configure.rabbitconfig.RabbitConfig;
import com.rabbitconsumer.demo.domain.ChannelBean;
import com.rabbitconsumer.demo.domain.RabbitChannlConfigBind;
import com.rabbitconsumer.demo.enums.EnumIsStart;
import com.rabbitmq.client.*;
import com.rabbitconsumer.demo.pojo.Rabbitconfig;
import com.rabbitconsumer.demo.service.IRabbitconfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
* @Author wwy
* @Description 消息监听，启动时执行
* @Date 11:14 2019/12/16
* @Param
* @return
**/
@Component
@Order(value = 10)
public class CustomerListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerListener.class);


    @Autowired
    private IRabbitconfig rabbitconfigService;

    @Autowired
    private RabbitConfig rabbitConfig;

    public List<Channel> channels = Lists.newArrayList();

    private volatile ConcurrentHashMap<ChannelBean, Channel> concurrentHashMap = new ConcurrentHashMap<ChannelBean,Channel>();

    public synchronized ConcurrentHashMap<ChannelBean, Channel> getConcurrentHashMap() {
        return concurrentHashMap;
    }

    public synchronized void setConcurrentHashMap(ConcurrentHashMap<ChannelBean, Channel> concurrentHashMap) {
        this.concurrentHashMap = concurrentHashMap;
    }

    @PostConstruct
    public synchronized void customer() {
        Rabbitconfig condition = new Rabbitconfig();
        condition.setStatus(EnumIsStart.IS_START.getCode());
        condition.setOrderBy("id asc");
        List<Rabbitconfig> r = rabbitconfigService.getList(condition);
        ConnectionFactory connection = rabbitConfig.connectionFactory();

        if(concurrentHashMap.size() > 0){
            concurrentHashMap.clear();
        }

        for (int i = 0; i < r.size(); i++) {
            int j = i;
            String queueName = r.get(j).getRabbitmqQueueName();
            ChannelBean bean = new ChannelBean();
            /*RabbitChannlConfigBind bind = RabbitChannlConfigBind.builder().appId(r.get(j).getAppId())
                    .userId(r.get(j).getYqsUserId())
                    .build();*/
           /* Thread thread = new Thread() {
                public void run() {*/
            Channel channel = null;
            try {
                channel = connection.createConnection().createChannel(false);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //声明要关注的队列
                    try {
                        LOGGER.info(queueName);
                        channel.queueDeclare(queueName, true,
                                false, false, null);

                        //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
                        // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
                        /*bind.setChannel(channel);
                        bind.setQueueName(queueName);*/
                        Consumer consumer = new DefaultConsumer(channel) {
                            @Override
                            public void handleDelivery(String consumerTag, Envelope envelope,
                                                       AMQP.BasicProperties properties, byte[] body)
                                    throws IOException {
                                String message = new String(body, "UTF-8");
                                if(message.equals("Error")){
                                    throw new RuntimeException("Error");
                                }
                                LOGGER.info("=======================接受开始===========================");
                                LOGGER.info(MessageFormat.format("Customer = {0}, Received ={1}",
                                       queueName,message));
                                LOGGER.info("=======================接受结束===========================");

                            }
                        };
                        //自动回复队列应答 -- RabbitMQ中的消息确认机制
                        String tag = channel.basicConsume(queueName, true, consumer);
                        bean.setQueueName(queueName);
                        bean.setTag(tag);
                        concurrentHashMap.put(bean,channel);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
/*                }
            };
            thread.start();*/

        }
    }

}
