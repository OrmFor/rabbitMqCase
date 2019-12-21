package com.rabbitconsumer.demo.request;


import lombok.Data;

@Data
public class TestsRdisRequest {

    private String queueName;

    private Integer group;
}
