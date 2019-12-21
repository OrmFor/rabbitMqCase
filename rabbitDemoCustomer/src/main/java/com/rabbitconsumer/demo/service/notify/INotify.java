package com.rabbitconsumer.demo.service.notify;


import com.rabbitconsumer.demo.domain.MqMessage;

public interface INotify {

    String processNotifyByMsgType(MqMessage mqMessage);

    String[] msgType();
}
