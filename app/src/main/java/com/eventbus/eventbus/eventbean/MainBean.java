package com.eventbus.eventbus.eventbean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/10/11.
 * eventBus 传递信息bean
 */

public class MainBean implements Serializable{

    public String name;
    public String msg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "MainBean{" +
                "name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

}
