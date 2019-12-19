package com.rabbitconsumer.demo.dao;

import com.rabbitconsumer.demo.pojo.Rabbitconfig;
import cc.s2m.web.utils.webUtils.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface RabbitconfigMapper extends BaseDao<Rabbitconfig, Integer> {
}