package com.rabbitconsumer.demo.controller;

import com.rabbitconsumer.demo.domain.ChannelBean;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;


/**
 * @Author wwy
 * @Description redis处理监听到的数据
 * 添加，启用，暂停操作
 * @Date 11:15 2019/12/16
 * @Param
 * @return
 **/
public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private CustomerListener customerListener;

    @Autowired
    public Receiver(CountDownLatch latch) {
        this.latch = latch;
    }


    public void receiveMessage4Add(String messageQueue) {
        LOGGER.info("receiveMessage4Add <" + messageQueue + ">");
        ConcurrentHashMap<ChannelBean, Channel> channels = customerListener.getConcurrentHashMap();
        ChannelBean bean = null;
        Channel channel = null;
        try {
            for (Map.Entry<ChannelBean, Channel> entry : channels.entrySet()) {
                if (messageQueue.equals(entry.getKey().getQueueName())) {
                    channel = entry.getValue();
                    AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(messageQueue, true,
                            false, false, null);
                    if (declareOk.getConsumerCount() == 1) {
                        latch.countDown();
                        return;
                    }
                }
            }
            new ThreadReceiveProcess(messageQueue, connectionFactory, customerListener.getConcurrentHashMap()).start();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
        }
    }

    public void receiveMessage4Start(String messageQueue) {
        LOGGER.info("receiveMessage4Start <" + messageQueue + ">");
        ConcurrentHashMap<ChannelBean, Channel> channels = customerListener.getConcurrentHashMap();
        ChannelBean bean = null;
        Channel channel = null;
        try {
            for (Map.Entry<ChannelBean, Channel> entry : channels.entrySet()) {
                if (messageQueue.equals(entry.getKey().getQueueName())) {
                    channel = entry.getValue();
                    AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(messageQueue, true,
                            false, false, null);
                    if (declareOk.getConsumerCount() == 1) {
                        LOGGER.info(MessageFormat.format(
                                "messageQueue:{0},tag:{1}",
                                entry.getKey().getQueueName(),
                                entry.getKey().getTag()));
                        channel.basicCancel(entry.getKey().getTag());
                        bean = entry.getKey();
                        //关闭信道
                        channel.close();
                        if (bean != null) {
                            channels.remove(bean);
                        }

                    }
                }
            }

            new ThreadReceiveProcess(messageQueue, connectionFactory, customerListener.getConcurrentHashMap()).start();

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            latch.countDown();
        }
    }

    public void receiveMessage4Stop(String messageQueue) {
        LOGGER.info("receiveMessage4Stop <" + messageQueue + ">");
        ConcurrentHashMap<ChannelBean, Channel> channels = customerListener.getConcurrentHashMap();
        ChannelBean bean = null;
        Channel channel = null;
        //声明要关注的队列
        try {
            for (Map.Entry<ChannelBean, Channel> entry : channels.entrySet()) {
                if (messageQueue.equals(entry.getKey().getQueueName())) {
                    channel = entry.getValue();
                    AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(messageQueue, true,
                            false, false, null);

                    if (declareOk.getConsumerCount() == 1) {
                        LOGGER.info(MessageFormat.format(
                                "messageQueue:{0},tag:{1}",
                                entry.getKey().getQueueName(),
                                entry.getKey().getTag()));
                        channel.basicCancel(entry.getKey().getTag());
                        bean = entry.getKey();
                        try {
                            //关闭信道
                            channel.close();
                            if (bean != null) {
                                channels.remove(bean);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
        }


    }
}
