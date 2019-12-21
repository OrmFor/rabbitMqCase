package com.rabbitconsumer.demo.service.notify.impl;

import com.rabbitconsumer.demo.domain.MqMessage;
import com.rabbitconsumer.demo.service.notify.CreateNotifyServiceAdapter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;


@Service
@Order(value = 2)
public class MsgOrderCancelService extends CreateNotifyServiceAdapter {



    @Override
    public String processNotifyByMsgType(MqMessage mqMessage) {

        return "success";
    }

    @Override
    public String[] msgType() {
        return new String[]{
               MqMessage.MSG_ORDER_CANCEL
        };
    }
}
