package com.baimsg.thread;

import com.baimsg.bean.User;
import com.baimsg.dictionary.DictionaryContext;

/**
 * create by baimsg 2021/11/18
 * Email 1469010683@qq.com
 **/
public class DictionaryThread implements Runnable {

    private final User my;

    public DictionaryThread(User user) {
        this.my = user;
    }

    @Override
    public void run() {
        User user = new DictionaryContext(my).login();
        String str = user.getId() + "\tapp：" + my.getChannel() + "\t账号：" + my.getPhone() + "\t密码：" + my.getPassword();
        if (user.isSuccess()) {
            System.err.println(str + "\n" + user.getUserName() + " --> 登陆成功\ttoken：" + user.getToken());
            DictionaryThreadPoolExecutor.threadPoolExecutor.shutdownNow();
        } else {
            if (!DictionaryThreadPoolExecutor.threadPoolExecutor.isShutdown()) {
                System.out.println(str + "\n" + user.getMessage());
            }
        }
    }
}
