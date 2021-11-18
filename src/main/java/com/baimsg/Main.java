package com.baimsg;


import com.baimsg.bean.User;
import com.baimsg.dictionary.DictionaryContext;
import com.baimsg.thread.MyThread;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;

public class Main {


    public static boolean isOk = false;
    /**
     * 请求频率/毫秒
     */
    private static final long delay = 0;

    /**
     * 账号
     */
    private static final String userName = "110099";

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
    }

    public static void main(String[] args) {
        BufferedReader br = null;
        URL resource = Main.class.getResource("/password.ini");
        try {
            if (resource != null) {
                br = new BufferedReader(new FileReader(resource.getFile()));
                String s;
                while ((s = br.readLine()) != null) {
                    if (isOk) break;
                    User my = new User(KEYS.get(0), userName, s);
                    new Thread(new MyThread(my)).start();
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
