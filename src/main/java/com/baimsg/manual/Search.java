package com.baimsg.manual;

import com.baimsg.Config;
import com.baimsg.utils.extension.FileExtensionKt;

import java.io.BufferedReader;
import java.io.File;
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
        for (String userName : Config.userNames) {
            File output = FileExtensionKt.appendPath(FileExtensionKt.toFile(Config.OUT_PATH), userName + ".ini");
            if (output.isFile()) {
                Map<String, String> dics = new HashMap<>();
                System.out.println(output.getName());
                for (String line : FileExtensionKt.readLines(output)) {
                    if (!line.startsWith("#")) {
                        String[] strs = line.split("\t");
                        if (!dics.containsKey(strs[2])) {
                            dics.put(strs[2], strs[1]);
                            System.out.println(line);
                        }
                    }
                }
            }
        }

    }
}
