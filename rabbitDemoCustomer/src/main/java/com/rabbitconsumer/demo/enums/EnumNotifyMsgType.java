package com.rabbitconsumer.demo.enums;

public enum EnumNotifyMsgType {

    MSG_ORDER_SEND("MSG_ORDER_SEND","订单发货"),
    MSG_ORDER_CANCLE("MSG_ORDER_CANCLE","订单取消"),
    MSG_GOOD_ON("MSG_GOOD_ON","商品上架"),
    MSG_GOOD_OFF("MSG_GOOD_OFF","商品下架"),
    MSG_GOOD_UPDATE("MSG_GOOD_UPDATE","商品修改"),
    MSG_TRANSPORT_UPDATE("MSG_TRANSPORT_UPDATE","运费模板修改"),
    ;

    private String msgType;

    private String name;

    EnumNotifyMsgType(String msgType, String name) {
        this.msgType = msgType;
        this.name = name;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
