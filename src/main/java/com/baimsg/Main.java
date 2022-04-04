package com.baimsg;


import com.baimsg.thread.TaskThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final HashMap<String, String> users = new HashMap<>();

    /**
     * 可登录的 app
     */
    private static final ArrayList<String> KEYS = new ArrayList<>();

    static {

        KEYS.add("酷聊");// 0
        KEYS.add("梦想");// 1
        KEYS.add("友聊");// 2
        KEYS.add("名信");// 3
        KEYS.add("探Mi");// 4
        KEYS.add("有料");// 5
        KEYS.add("e信");// 6
        KEYS.add("叮当");// 7
        KEYS.add("蘑菇云");// 8
        KEYS.add("传信");// 9
        KEYS.add("呱呱");// 10
        KEYS.add("同聊");// 11
        KEYS.add("微彩聊");// 12
        KEYS.add("微聊");// 13

        //账号列表 前面账号后面app名字
        users.put("baimsg", KEYS.get(0));
        users.put("fuyguiho", KEYS.get(0));
        users.put("tyojijh", KEYS.get(0));
        users.put("pjghhbn", KEYS.get(0));
        users.put("guriifsd", null);

    }

    /**
     * app名字
     */
    private static final String appName = KEYS.get(13);

    public static void main(String[] args) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(Config.maxThread);

        if (Config.LOG_PATH.isFile()) {
            boolean delete = Config.LOG_PATH.delete();
            if (delete) {
                System.out.println(Config.LOG_PATH + "已清空");
            }
        }
        for (Map.Entry<String, String> entry : users.entrySet()) {
            String app = (entry.getValue() == null) ? appName : entry.getValue();
            String userName = entry.getKey();
            fixedThreadPool.execute(new TaskThread(app, userName));
        }
        fixedThreadPool.shutdown();
    }

}
