package com.rabbitconsumer.demo.enums;

public enum EnumIsStart {

    IS_START(1,"启动"),
    IS_STOP(0,"暂停"),
    IS_STOP_RECEIVE(2,"暂停接收"),
    ;

    private int code;

    private String name;

    EnumIsStart(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
