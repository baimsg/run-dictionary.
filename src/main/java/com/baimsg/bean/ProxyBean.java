package com.baimsg.bean;

import java.util.ArrayList;

public class ProxyBean {
    private int state;
    private String msg;
    private ArrayList<ProxyInfo> data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<ProxyInfo> getData() {
        return data;
    }

    public void setData(ArrayList<ProxyInfo> data) {
        this.data = data;
    }

}
