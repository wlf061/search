package com.struggle.service.constant;

import static javafx.scene.input.KeyCode.O;

/**
 * 类的描述
 *
 * @author nancy.wang
 * @Time 2018/12/9
 */
public enum RequestType {

    ORDER_PRICE(0,"价格排序"),

    ORDER_STAR(1,"打分排序"),

    ORDER_CREATETIME(2,"更新时间"),

    ORDER_COMPLEX(3,"综合排序");

    private int code;
    private String desc;

    RequestType(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static RequestType getByCode(int value) {
        for (RequestType requestType : values()) {
            if (requestType.getCode() == value) {
                return requestType;
            }
        }
        return null;
    }
}
