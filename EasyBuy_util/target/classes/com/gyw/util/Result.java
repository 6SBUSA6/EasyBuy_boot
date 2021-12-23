package com.gyw.util;

/**
 * @author 高源蔚
 * @date 2021/12/15-11:14
 * @describe
 */
public class Result {
    private Integer code; //状态码，代表给码代表不同的错误
    private String status;
    private String message; //消息
    private Object object; //传的值

    private Result() {
    }

    public static Result getPageResult(Integer code, String status, String message, Object object){
        Result result = new Result();
        result.setCode(code);
        result.setStatus(status);
        result.setMessage(message);
        result.setObject(object);
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
