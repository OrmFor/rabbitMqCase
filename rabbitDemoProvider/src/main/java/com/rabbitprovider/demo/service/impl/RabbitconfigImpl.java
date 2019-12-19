package com.rabbitprovider.demo.service.impl;
import com.rabbitprovider.demo.dao.RabbitconfigMapper;
import com.rabbitprovider.demo.pojo.Rabbitconfig;
import com.rabbitprovider.demo.service.IRabbitconfig;
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