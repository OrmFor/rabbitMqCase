package com.rabbitconsumer.demo.observe;

import com.rabbitconsumer.demo.controller.CustomerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Observable;


@Component
public class ObserveThread extends Observable implements Runnable {
    // 此方法一经调用，立马可以通知观察者，在本例中是监听线程
    public void doBusiness() {
        if (true) {
            super.setChanged();
        }
        notifyObservers();
    }

    @Autowired
    private CustomerListener customerListener;

    @Override
    public void run() {
        while (true) {
            try {
               //customerListener.customer();
            } catch (Exception e) {
                e.printStackTrace();
                doBusiness();
                break;
            }
        }
    }

/*    public static void main(String[] args) {
        ObserveThread run = new ObserveThread();
        ObserveListener listen = new ObserveListener();
        run.addObserver(listen);
        new Thread(run).start();
    }

    @PostConstruct
    public void test(){
        this.run();
    }*/

}