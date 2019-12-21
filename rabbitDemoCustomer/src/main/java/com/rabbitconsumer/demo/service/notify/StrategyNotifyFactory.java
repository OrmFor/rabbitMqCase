package com.rabbitconsumer.demo.service.notify;

import com.rabbitconsumer.demo.enums.EnumNotifyMsgType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component(value = "StrategyNotifyFactory")
public class StrategyNotifyFactory implements ApplicationContextAware {
    private static final Logger LOGGER = LogManager.getLogger(StrategyNotifyFactory.class);

    private static StrategyNotifyFactory factory = new StrategyNotifyFactory();

    private ApplicationContext applicationContext;

    @Autowired(required = false)
    private List<INotify> services;

    private static Map<String, INotify> map;


/*    @Override
    public void afterPropertiesSet() throws Exception {
        map = new HashMap<String, INotify>();
        if (CollectionUtils.isEmpty(services)){
            return;
        }
        for (INotify s : services) {
            for (String key : s.msgType()) {
                map.put(key, s);
            }
        }
    }*/

    public INotify creator(String msgType) {
        return getDelegate(msgType);
    }

    public INotify creator(EnumNotifyMsgType type) {
        return getDelegate(type.getMsgType());
    }

    public static StrategyNotifyFactory getInstance() {
        return factory;
    }


    private INotify getDelegate(String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        throw new RuntimeException(String.format("StrategyNotifyFactory ： 暂不支持的业务种类%s", key));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        map = new HashMap<String, INotify>();
        if (CollectionUtils.isEmpty(services)){
            return;
        }
        for (INotify s : services) {
            for (String key : s.msgType()) {
                map.put(key, s);
            }
        }

        this.applicationContext = applicationContext;

    }
}
