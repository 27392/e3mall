package cn.haohaoli.pojo;

import java.io.Serializable;

/**
 * 返回类
 * @author Liwenhao
 * @date 2018/8/9 14:59
 */
public class E3Result implements Serializable {

    private Integer status;
    private String message;
    private Object data;

    public E3Result() {

    }

    private E3Result(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static E3Result ok(Object data) {
        return E3Result.build(200,"OK",data);
    }

    public static E3Result ok() {
        return E3Result.build(200,"OK",null);
    }

    public static E3Result error(String msg) {
        return E3Result.build(500,msg,null);
    }

    public static E3Result error(String msg, Object data) {
        return E3Result.build(404,msg,data);
    }

    public static E3Result build(Integer status, String msg, Object data) {
        return new E3Result(status, msg, data);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
