package com.zte.zshop.utils;

import com.zte.zshop.constant.Constants;

/**
 * Author:helloboy
 * Date:2019-05-16 16:51
 * Description:<描述>
 *     封装一个返回对象，用于组装json对象返回
 */
public class ResponseResult {

    //状态码
    private Integer status;
    //消息
    private String message;
    //返回数据
    private Object data;

    public ResponseResult() {
    }

    public ResponseResult(Integer status, String message,Object data) {
        this.status = status;
        this.data = data;
        this.message = message;
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

    public static ResponseResult success(Object obj){
        return new ResponseResult(Constants.RESPONSE_STATUS_SUCCESS,"success",obj);
    }

    public static ResponseResult success(String message,Object obj){
        return new ResponseResult(Constants.RESPONSE_STATUS_SUCCESS,message,obj);
    }

    public static ResponseResult success(String message){
        return new ResponseResult(Constants.RESPONSE_STATUS_SUCCESS,message,null);
    }

    public static ResponseResult fail(String message){
        return new ResponseResult(Constants.RESPONSE_STATUS_FAILURE,message,null);
    }
}















