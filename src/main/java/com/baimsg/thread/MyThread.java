package com.baimsg.thread;

import com.baimsg.Main;
import com.baimsg.bean.User;
import com.baimsg.dictionary.DictionaryContext;

/**
 * create by baimsg 2021/11/18
 * Email 1469010683@qq.com
 **/
public class MyThread implements Runnable {

    private final User my;

    public MyThread(User user) {
        this.my = user;
    }

    @Override
    public void run() {
        User user = new DictionaryContext(my).login();
        System.out.println("app：" + my.getChannel() + "\t账号：" + my.getPhone() + "\t密码：" + my.getPassword());
        if (user.isSuccess()) {
            System.err.println("登陆成功·" + user.getUserName() + "\ttoken：" + user.getToken());
            Main.isOk = true;
        } else {
            System.out.println(user.getMessage());
        }
    }
}
