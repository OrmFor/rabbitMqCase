package com.rabbitprovider.demo.dao;

import com.rabbitprovider.demo.pojo.Rabbitconfig;
import cc.s2m.web.utils.webUtils.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
public interface RabbitconfigMapper extends BaseDao<Rabbitconfig, Integer> {
}