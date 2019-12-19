package com.rabbitconsumer.demo.service.impl;
import com.rabbitconsumer.demo.dao.RabbitconfigMapper;
import com.rabbitconsumer.demo.pojo.Rabbitconfig;
import com.rabbitconsumer.demo.service.IRabbitconfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class RabbitconfigImpl extends BaseServiceImpl<Rabbitconfig, RabbitconfigMapper, Integer> implements IRabbitconfig {
    @Autowired
    private RabbitconfigMapper rabbitconfigMapper;

    protected RabbitconfigMapper getDao() {
        return rabbitconfigMapper;
    }
}