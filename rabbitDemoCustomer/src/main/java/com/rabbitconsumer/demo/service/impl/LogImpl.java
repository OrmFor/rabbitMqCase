package com.rabbitconsumer.demo.service.impl;

import com.rabbitconsumer.demo.dao.LogMapper;
import com.rabbitconsumer.demo.pojo.Log;
import com.rabbitconsumer.demo.service.ILog;
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