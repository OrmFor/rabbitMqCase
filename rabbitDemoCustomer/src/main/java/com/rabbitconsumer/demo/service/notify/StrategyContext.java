package com.rabbitconsumer.demo.service.notify;

import com.rabbitconsumer.demo.domain.MqMessage;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
* @Author wwy
* @Description 根据所使用的类型生成相对应的订单
* @Date 9:35 2018/12/17
* @Param
* @return
**/
@Component(value = "StrategyContext")
@DependsOn(value = "StrategyNotifyFactory")
public class StrategyContext {

    private INotify strategy;

    public String processNotifyByMsgType(MqMessage mqMessage) {
        if(mqMessage != null) {
            strategy = StrategyNotifyFactory.getInstance().creator(mqMessage.getMsgType());
            return strategy.processNotifyByMsgType(mqMessage);
        }

        return "success";
    }
}
