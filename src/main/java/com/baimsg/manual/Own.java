package com.baimsg.manual;

import com.baimsg.Config;
import com.baimsg.thread.DictionaryThreadPoolExecutor;
import com.baimsg.thread.SimpleThread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.net.URL;
import java.util.Scanner;

/**
 * create by baimsg 2021/11/26
 * Email 1469010683@qq.com
 * <p>
 * 实现了手动测试
 **/
public class Own {

    public static void main(String[] args) {
        System.err.println("是否清空上次的记录？\n输入 Y 清空\t回车不清空");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        if (str.equalsIgnoreCase("Y")) {
            boolean delete = new File(Config.PATH).delete();
            if (delete) {
                System.out.println("清空成功！" + Config.PATH);
            } else {
                System.out.println("清空失败！" + Config.PATH);
            }
        }
        BufferedReader br = null;
        URL resource = com.baimsg.Main.class.getResource("/password.ini");
        try {
            if (resource != null) {
                br = new BufferedReader(new FileReader(resource.getFile()));
                String s;
                BigInteger index = new BigInteger("0");
                while ((s = br.readLine()) != null) {
                    index = index.add(new BigInteger("1"));
                    DictionaryThreadPoolExecutor.threadPoolExecutor.execute(new SimpleThread(index, s));
                }
                DictionaryThreadPoolExecutor.threadPoolExecutor.shutdown();
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
