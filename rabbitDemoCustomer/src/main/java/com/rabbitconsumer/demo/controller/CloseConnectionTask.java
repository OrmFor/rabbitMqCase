package com.rabbitconsumer.demo.controller;

import com.rabbitconsumer.demo.domain.ChannelBean;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;


/**
 * @Author wwy
 * @Description 消费端模拟rabbitMq信道发生异常
 * @Date 11:14 2019/12/16
 * @Param
 * @return
 **/
@Component
@EnableScheduling
public class CloseConnectionTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(CloseConnectionTask.class);


    @Autowired
    private CustomerListener customerListener;

    @Scheduled(cron = "0 */5 * * * ?")
    public void restartThread() {
        ConcurrentHashMap<ChannelBean, Channel> channels = customerListener.getConcurrentHashMap();


        for (Map.Entry<ChannelBean, Channel> entry : channels.entrySet()) {
            Channel channel = entry.getValue();
            try {
                ChannelBean bean = entry.getKey();
                LOGGER.info(bean.toString());
                String tag = bean.getTag();

                AMQP.Queue.DeclareOk declareOk= channel.queueDeclare(bean.getQueueName(), true,
                        false, false, null);

                int consumerCount = declareOk.getConsumerCount();
                if(consumerCount == 1){
                    channel.basicCancel(bean.getTag());
                    channel.close();

                    channels.put(bean, null);
                }
               // channel.basicCancel(tag);

            } catch (Exception e) {
                e.printStackTrace();


            }finally {
               /* try {
                   // Connection connection = channel.getConnection();
                    channel.close();
                  //  connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }*/
            }

            //channel.getConnection().close();
            //channels.remove(entry.getKey());
        }

           /* customerListener.setConcurrentHashMap(
                    channels
            );*/

    }

}
