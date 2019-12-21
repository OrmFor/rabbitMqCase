package com.rabbitconsumer.demo.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ShutdownSignalException;
import org.springframework.amqp.rabbit.connection.ShutDownChannelListener;
import org.springframework.stereotype.Component;

@Component
public class ShoutDownListener implements ShutDownChannelListener {
    @Override
    public void onCreate(Channel channel, boolean transactional) {
        System.out.println("shutdown");
    }

    @Override
    public void onShutDown(ShutdownSignalException signal) {

        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    }
}
