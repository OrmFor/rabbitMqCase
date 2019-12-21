package com.rabbitconsumer.demo.service.notify.impl;

import com.alibaba.fastjson.JSON;
import com.rabbitconsumer.demo.domain.MqMessage;
import com.rabbitconsumer.demo.pojo.Log;
import com.rabbitconsumer.demo.service.ILog;
import com.rabbitconsumer.demo.service.notify.CreateNotifyServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@Order(value = 2)
public class MsgGoodOnService extends CreateNotifyServiceAdapter {

    @Autowired
    private ILog logService;

    @Override
    public String processNotifyByMsgType(MqMessage mqMessage) {

        Log log = new Log();
        log.setCreateTime(new Date());
        log.setQueueName(mqMessage.getQueueName());
        log.setRemark(JSON.toJSONString(mqMessage));
        log.setType("消费者");

        logService.insert(log);
        return "success";
    }

    @Override
    public String[] msgType() {
        return new String[]{
               MqMessage.MSG_GOODS_ON
        };
    }
}
