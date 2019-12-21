package com.rabbitconsumer.demo.service.notify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class CreateNotifyServiceAdapter implements INotify {
    private static final Logger logger = LoggerFactory.getLogger(CreateNotifyServiceAdapter.class);


    public Map<String,String> setConfig(){
        Map<String,String> config = new HashMap<>();
        config.put("host","");

        return null;
    }

}
