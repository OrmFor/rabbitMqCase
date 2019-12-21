package com.rabbitprovider.demo.controller;

import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.rabbitmq.client.Channel;
import com.rabbitprovider.demo.configure.RabbitMqGroup;
import com.rabbitprovider.demo.configure.rabbitconfig.RabbitProdConfig;
import com.rabbitprovider.demo.domain.MqMessage;
import com.rabbitprovider.demo.enums.EnumIsStart;
import com.rabbitprovider.demo.pojo.Log;
import com.rabbitprovider.demo.pojo.Rabbitconfig;
import com.rabbitprovider.demo.service.ILog;
import com.rabbitprovider.demo.service.IRabbitconfig;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;


@Component
@EnableScheduling
public class ProviderTaskB {

    @Autowired
    private IRabbitconfig rabbitconfigService;

    @Autowired
    private RabbitProdConfig rabbitProdConfig;

    @Autowired
    private ILog logService;

    @Autowired
    private RabbitMqGroup rabbitMqGroup;

    private int j = 15;


    @Scheduled(cron = "30 */20 * * * ?")
    public void test() throws Exception {
        Rabbitconfig condi = new Rabbitconfig();
        condi.and(Expressions.in("status", Lists.newArrayList(EnumIsStart.IS_START.getCode(), 2)));
        condi.setGroup(2);
        List<Rabbitconfig> r = rabbitconfigService.getList(condi);

        ConnectionFactory connection = rabbitProdConfig.connectionFactory();

        for (Rabbitconfig rabbitconfig : r) {

            for (int k = 0; k < 10; k++) {
                Thread thread = new Thread() {
                    public void run() {

                        try {
                            Channel channel = connection.createConnection().createChannel(false);
                            //  声明一个队列

                            channel.queueDeclare(rabbitconfig.getRabbitmqQueueName(),
                                    true, false, false, null);

                            MqMessage bean = new MqMessage();
                            bean.setMsgType("MSG_ORDER_SEND");
                            bean.setContent("发货");
                            bean.setDesc("发货");

                            //发送消息到队列中
                            channel.basicPublish("", rabbitconfig.getRabbitmqQueueName(),
                                    null, JSON.toJSONBytes(bean));

                            Log log = new Log();
                            log.setQueueName(rabbitconfig.getRabbitmqQueueName());
                            log.setRemark(JSON.toJSONString(bean));
                            log.setType("生产者");
                            log.setCreateTime(new Date());
                            log.setUpdateTime(new Date());

                            logService.insert(log);

                            //关闭通道和连接
                            channel.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                };
                thread.start();
            }
        }
    }

  /*  @RequestMapping("/api/1.0")
    public String index() throws Exception {
        List<Rabbitconfig> r = rabbitconfigService.getList();
        *//*Connection connection = rabbitConifgUtil.init().get("1");*//*
        Map<String,Connection> map = rabbitConifgUtil.connData;

        for(Rabbitconfig rabbitconfig : r ) {
            Connection connection = map.get(rabbitconfig.getRabbitmqUserName());
            Channel channel = connection.createChannel();
            //  声明一个队列
            channel.queueDeclare(rabbitconfig.getRabbitmqQueueName(), false, false, false, null);
            String message = "Hello RabbitMQ";
            int i = 0;
            //发送消息到队列中
            channel.basicPublish("", rabbitconfig.getRabbitmqQueueName(), null, message.getBytes("UTF-8"));
            System.out.println("Producer Send +'" + message + "'");
            //关闭通道和连接


            channel.close();
        }
        return "success";
    }*/


//    @Scheduled(cron = "*/5 * * * * ?")
//    public void test() throws Exception {
//        Rabbitconfig condi = new Rabbitconfig();
//        condi.setRabbitmqUserName("admin");
//        Rabbitconfig rabbitconfig = rabbitconfigService.getByCondition(condi);
//        Map<String, ConnectionFactory> map = rabbitConifgUtil.connData;
//
//
//        System.out.println(rabbitconfig.getRabbitmqUserName());
//
//        //创建一个通道
//        Channel channel = map.get(rabbitconfig.getRabbitmqUserName()).newConnection().createChannel();
//        //声明要关注的队列
//
//        channel.queueDeclare(rabbitconfig.getRabbitmqQueueName(), false, false, false, null);
//        System.out.println("Customer Waiting Received messages");
//        //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
//        // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
//        Consumer consumer = new DefaultConsumer(channel) {
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope,
//                                       AMQP.BasicProperties properties, byte[] body)
//                    throws IOException {
//                String message = new String(body, "UTF-8");
//                System.out.println("Customer Received '" + message + "'");
//            }
//        };
//        //自动回复队列应答 -- RabbitMQ中的消息确认机制
//        channel.basicConsume(rabbitconfig.getRabbitmqQueueName(), true, consumer);
//
//    }
}
