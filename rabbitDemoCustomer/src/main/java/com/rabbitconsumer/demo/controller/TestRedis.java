package com.rabbitconsumer.demo.controller;


import cc.s2m.web.utils.webUtils.util.JSONResultCode;
import com.rabbitconsumer.demo.configure.RabbitMqGroup;
import com.rabbitconsumer.demo.enums.EnumIsStart;
import com.rabbitconsumer.demo.pojo.Rabbitconfig;
import com.rabbitconsumer.demo.request.TestsRdisRequest;
import com.rabbitconsumer.demo.service.IRabbitconfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;

@RestController
public class TestRedis {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CountDownLatch latch;

    @Autowired
    private RabbitMqGroup rabbitMqGroup;

    @Autowired
    private IRabbitconfig rabbitconfigService;

    @RequestMapping("/addUser")
    public JSONResultCode testRedis(@RequestBody TestsRdisRequest testsRdisRequest){

        if(testsRdisRequest.getGroup() == null ||
                (testsRdisRequest.getGroup() != null && testsRdisRequest.getGroup() != rabbitMqGroup.getGroup() )){
            return new JSONResultCode(500,"分组错误");
        }

        stringRedisTemplate.convertAndSend("addUser"+rabbitMqGroup.getGroup(), testsRdisRequest.getQueueName());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new JSONResultCode(200,"success");
    }


    @RequestMapping("/startUser")
    public JSONResultCode addYqsUser(@RequestBody TestsRdisRequest testsRdisRequest){
        if(testsRdisRequest.getGroup() == null ||
                (testsRdisRequest.getGroup() != null && testsRdisRequest.getGroup() != rabbitMqGroup.getGroup() )){
            return new JSONResultCode(500,"分组错误");
        }

        Rabbitconfig condi = new Rabbitconfig();
        condi.setRabbitmqQueueName(testsRdisRequest.getQueueName());
        condi.setGroup(testsRdisRequest.getGroup());

        Rabbitconfig update = new Rabbitconfig();
        update.setStatus(EnumIsStart.IS_START.getCode());

        rabbitconfigService.updateByCondition(update,condi);
        stringRedisTemplate.convertAndSend("startUser"+rabbitMqGroup.getGroup(), testsRdisRequest.getQueueName());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new JSONResultCode(200,"success");

    }


    @RequestMapping("/stopUser")
    public JSONResultCode stopYqsUser(@RequestBody TestsRdisRequest testsRdisRequest){
        if(testsRdisRequest.getGroup() == null ||
                (testsRdisRequest.getGroup() != null && testsRdisRequest.getGroup() != rabbitMqGroup.getGroup() )){
            return new JSONResultCode(500,"分组错误");
        }
        Rabbitconfig condi = new Rabbitconfig();
        condi.setRabbitmqQueueName(testsRdisRequest.getQueueName());
        condi.setGroup(testsRdisRequest.getGroup());

        Rabbitconfig update = new Rabbitconfig();
        update.setStatus(EnumIsStart.IS_STOP_RECEIVE.getCode());

        rabbitconfigService.updateByCondition(update,condi);

        stringRedisTemplate.convertAndSend("stopUser"+rabbitMqGroup.getGroup(), testsRdisRequest.getQueueName());



        try {
            Thread.sleep(10000);
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new JSONResultCode(200,"success");

    }
}
