package com.rabbitconsumer.demo.domain;


import java.io.Serializable;

public class MqMessage extends BaseRequest implements Serializable {

    public static final String MSG_ORDER_SEND = "MSG_ORDER_SEND";//订单发货
    public static final String MSG_ORDER_CANCEL = "MSG_ORDER_CANCEL";//订单取消
    public static final String MSG_GOODS_ON = "MSG_GOODS_ON";//商品上架
    public static final String MSG_GOODS_OFF = "MSG_GOODS_OFF";//商品下架
    public static final String MSG_GOODS_UPDATE = "MSG_GOODS_UPDATE";//商品修改
    public static final String MSG_TRANSPORT_UPDATE = "MSG_TRANSPORT_UPDATE";//运费模版修改

    private String msgType;
    private String content;
    private String desc;

    public MqMessage(){}

    public MqMessage(String msgType, String content, String desc) {
        this.msgType = msgType;
        this.content = content;
        this.desc = desc;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "MqMessage{" +
                "msgType='" + msgType + '\'' +
                ", content='" + content + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
