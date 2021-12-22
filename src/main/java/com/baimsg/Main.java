package com.baimsg;


import com.baimsg.bean.User;
import com.baimsg.thread.DictionaryThread;
import com.baimsg.thread.DictionaryThreadPoolExecutor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;

public class Main {

    /**
     * 账号
     */
    private static final String userName = "baimsg";

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
    }

    /**
     * app名字
     */
    private static final String appName = KEYS.get(0);

    public static void main(String[] args) {
        BufferedReader br = null;
        URL resource = Main.class.getResource("/password.ini");
        try {
            if (resource != null) {
                br = new BufferedReader(new FileReader(resource.getFile()));
                String s;
                BigInteger index = new BigInteger("0");
                while ((s = br.readLine()) != null) {
                    index = index.add(new BigInteger("1"));
                    DictionaryThreadPoolExecutor.threadPoolExecutor.execute(
                            new DictionaryThread(new User(index, appName, userName, s)
                            )
                    );
                }
                br.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert br != null;
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
