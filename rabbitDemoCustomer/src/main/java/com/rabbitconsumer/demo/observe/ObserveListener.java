package com.rabbitconsumer.demo.observe;

import com.rabbitconsumer.demo.controller.CustomerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Observable;
import java.util.Observer;


@Component
public class ObserveListener implements Observer {

    @Autowired
    private CustomerListener customerListener;

    @Override
    public void update(Observable o, Object arg) {
        //重启
        customerListener.customer();

    }

}