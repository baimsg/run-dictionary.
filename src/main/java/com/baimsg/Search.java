package com.baimsg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 查看密码的类
 * <p>
 * create by baimsg 2021/12/10
 * Email 1469010683@qq.com
 **/
public class Search {
    public static void main(String[] args) {
        FileReader fr;
        try {
            fr = new FileReader(Config.PATH);
            BufferedReader br = new BufferedReader(fr);
            String line;
            Map<String, String> dics = new HashMap<>();
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#")) {
                    String[] strs = line.split("\t");
                    if (!dics.containsKey(strs[2])) {
                        dics.put(strs[2], strs[1]);
                        System.out.println(line);
                    }
                }
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
