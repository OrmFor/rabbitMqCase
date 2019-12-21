package com.rabbitconsumer.demo.service.notify.impl;

import com.rabbitconsumer.demo.domain.MqMessage;
import com.rabbitconsumer.demo.service.notify.CreateNotifyServiceAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;


/**
* @Author wwy
* @Description 修改商品
* @Date 11:18 2019/11/29
* @Param
* @return
**/
@Service
@Order(value = 2)
public class MsgGoodUpdateService extends CreateNotifyServiceAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());



    @Override
    public String processNotifyByMsgType(MqMessage mqMessage) {
        this.modifyGoodsFromYqs(mqMessage);
        return "success";
    }

    @Override
    public String[] msgType() {
        return new String[]{
                MqMessage.MSG_GOODS_UPDATE
        };
    }


    public void modifyGoodsFromYqs(MqMessage mqMessage){

    }

}
