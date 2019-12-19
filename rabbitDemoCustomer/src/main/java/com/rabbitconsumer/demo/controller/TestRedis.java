package com.rabbitconsumer.demo.controller;


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
    private IRabbitconfig rabbitconfigService;

    @RequestMapping("/addUser")
    public void testRedis(@RequestBody TestsRdisRequest testsRdisRequest){
        stringRedisTemplate.convertAndSend("addUser", testsRdisRequest.getQueueName());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/startUser")
    public void addYqsUser(@RequestBody TestsRdisRequest testsRdisRequest){
        Rabbitconfig condi = new Rabbitconfig();
        condi.setRabbitmqQueueName(testsRdisRequest.getQueueName());

        Rabbitconfig update = new Rabbitconfig();
        update.setStatus(EnumIsStart.IS_START.getCode());

        rabbitconfigService.updateByCondition(update,condi);
        stringRedisTemplate.convertAndSend("startUser", testsRdisRequest.getQueueName());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/stopUser")
    public void stopYqsUser(@RequestBody TestsRdisRequest testsRdisRequest){
        Rabbitconfig condi = new Rabbitconfig();
        condi.setRabbitmqQueueName(testsRdisRequest.getQueueName());

        Rabbitconfig update = new Rabbitconfig();
        update.setStatus(EnumIsStart.IS_STOP_RECEIVE.getCode());

        rabbitconfigService.updateByCondition(update,condi);

        stringRedisTemplate.convertAndSend("stopUser", testsRdisRequest.getQueueName());

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
