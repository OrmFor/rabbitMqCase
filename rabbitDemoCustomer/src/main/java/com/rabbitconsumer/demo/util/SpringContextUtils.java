package com.rabbitconsumer.demo.util;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class SpringContextUtils implements ApplicationContextAware {// Spring的工具类，用来获得配置文件中的bean
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }


    public static <T> T getBeanByClass(Class<T> c){
        return context.getBean(c);
    }
    public static Object getBeanByName(String name){
        return context.getBean(name);
    }
}


