package com.baimsg.bean;

import java.math.BigInteger;

public class User {

    /**
     * 任务序号
     */
    private BigInteger id;
    /**
     * 渠道号
     */
    private String channel;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户名称
     */
    private String UserName;

    /**
     * 登录token
     */
    private String token;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 登录成功标识
     */
    private boolean success;

    /**
     * 错误码
     */
    private int code;

    public User(BigInteger id, String channel, String phone, String password) {
        this.id = id;
        this.channel = channel;
        this.phone = phone;
        this.password = password;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
