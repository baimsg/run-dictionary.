package com.baimsg.manual;

import com.baimsg.Config;
import com.baimsg.thread.DictionaryThreadPoolExecutor;
import com.baimsg.thread.SimpleThread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * create by baimsg 2021/11/26
 * Email 1469010683@qq.com
 * <p>
 * 实现了手动测试
 **/
public class Own {

    public static void main(String[] args) {
        for (String userName : Config.userNames) {
            new Thread(new OwnThread(userName)).start();
        }
    }
}
