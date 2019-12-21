package com.rabbitprovider.demo.service.impl;

import com.rabbitprovider.demo.dao.LogMapper;
import com.rabbitprovider.demo.pojo.Log;
import com.rabbitprovider.demo.service.ILog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class LogImpl extends BaseServiceImpl<Log, LogMapper, Integer> implements ILog {
    @Autowired
    private LogMapper logMapper;

    protected LogMapper getDao() {
        return logMapper;
    }
}