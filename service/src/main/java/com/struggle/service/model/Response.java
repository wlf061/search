package com.struggle.service.model;

/**
 * 类的描述
 *
 * @author nancy.wang
 * @Time 2018/12/9
 */
public class Response<T> {
    private int code;
    private String message;
    private T data;
    public Response(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Response<T> success(int code, String message, T result) {
        return new Response<>(code, message,result);
    }
    public static <T>Response<T> fail(int code, String message, T result){
        return new Response<>(code, message,result);
    }
}
