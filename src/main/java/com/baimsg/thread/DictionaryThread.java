package com.baimsg.thread;

import com.baimsg.Config;
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
        login(0);
    }

    /**
     * 登录方法是独立（递归实现重试）
     *
     * @param retry 请求次数
     */
    private void login(int retry) {
        User user = new DictionaryContext(my).login();
        String str = user.getId() + "\tapp：" + my.getChannel() + "\t账号：" + my.getPhone() + "\t密码：" + my.getPassword();
        if (user.isSuccess()) {
            System.err.println(str + "\n" + user.getUserName() + " --> 登陆成功\ttoken：" + user.getToken());
            DictionaryThreadPoolExecutor.threadPoolExecutor.shutdownNow();
        } else {
            if (!DictionaryThreadPoolExecutor.threadPoolExecutor.isShutdown()) {
                if (user.getCode() == 404) {
                    if (retry < Config.RETRY) {
                        retry++;
                        System.out.println("第" + retry + "次重试ing\t" + str);
                        login(retry);
                    } else {
                        System.out.println(str + "\t请求失败！");
                    }
                } else {
                    System.out.println(str + "\n" + user.getMessage());
                }
            }
        }
    }
}
