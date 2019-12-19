//package com.rabbitprovider.demo;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.concurrent.CountDownLatch;
//
//
//public class Receiver1 {
//    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver1.class);
//
//    private CountDownLatch latch;
//
//    @Autowired
//    public Receiver1(CountDownLatch latch) {
//        this.latch = latch;
//    }
//
//    public void receiveMessage(String message) {
//        LOGGER.info("Received <" + message + ">");
//        latch.countDown();
//    }
//}
