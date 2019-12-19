package com.rabbitconsumer.demo.controller;

import com.rabbitconsumer.demo.domain.ChannelBean;
import com.rabbitmq.client.*;
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
public class ThreadsTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadsTask.class);


    @Autowired
    private CustomerListener customerListener;

    @Autowired
    private ConnectionFactory connectionFactory;


    @Scheduled(cron = "*/30 * * * * ?")
    public void restartThread() {
        ConcurrentHashMap<ChannelBean, Channel> channels = customerListener.getConcurrentHashMap();
        try {
            boolean flag;
            for (Map.Entry<ChannelBean, Channel> entry : channels.entrySet()) {
                ChannelBean channelBean = entry.getKey();
                try {
                    Channel channel = entry.getValue();
                    if (channel != null) {
                        LOGGER.info(MessageFormat.format("=================queueName{0}==================",
                                channelBean.toString()));
                        flag = channel.isOpen();
                        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(channelBean.getQueueName(), true,
                                false, false, null);
                        //查看是否有消费者绑定
                        int consumerCount = declareOk.getConsumerCount();
                        //consumerCount=0说明已经没有消费者
                        if ((flag && consumerCount == 0) || !flag) {
                            channel.close();
                            customerListener.getConcurrentHashMap().remove(channelBean);
                            new ThreadReceiveProcess(channelBean.getQueueName(), connectionFactory, channels).start();
                        }
                    }else {
                        //重新开启信道
                        new ThreadReceiveProcess(channelBean.getQueueName(), connectionFactory,  channels).start();
                    }

                } catch (IOException e1) {
                    e1.printStackTrace();
                   /* customerListener.concurrentHashMap.remove(queueName);
                    new ThreadReceiveProcess(queueName, connectionFactory).start();*/
                } catch (Exception e2) {
                    e2.printStackTrace();
                    /*customerListener.concurrentHashMap.remove(queueName);
                    new ThreadReceiveProcess(queueName, connectionFactory).start();*/
                }
            }


        } catch (Exception e) {
           /* if (channels.size() > 0) {
                for (Map.Entry<String, Channel> entry : channels.entrySet()) {
                    Channel channel = entry.getValue();
                    String queueName = entry.getKey();
                    try {
                        if (channel != null) {
                            String consumerTag = channel.basicConsume(
                                    queueName,
                                    new DefaultConsumer(channel) {
                                    });
                            channel.basicCancel(consumerTag);
                            channel.close();
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
            customerListener.customer();*/
        }
    }


 /*   public void restartThread1() {
        Rabbitconfig condition = new Rabbitconfig();
        condition.setStatus(EnumIsStart.IS_START.getCode());
        List<Rabbitconfig> rabbitconfigList = rabbitconfigService.getList(condition);
        List<Thread> threadList = customerListener.threads;
        List<String> threadNames = customerListener.threadNames;
        for (int i = 0; i < threadList.size(); i++) {
            System.out.println(MessageFormat.format("定时任务执行:{0}", threadList.get(i).getName()));
        }
    }*/

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
