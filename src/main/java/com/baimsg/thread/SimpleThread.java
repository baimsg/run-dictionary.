package com.baimsg.thread;

import com.baimsg.Config;
import com.baimsg.network.HttpUtils;
import com.baimsg.utils.SafetyUtil;
import com.baimsg.utils.extension.FileExtensionKt;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * create by baimsg 2021/11/26
 * Email 1469010683@qq.com
 * <p>
 * 手动测试类
 **/
public class SimpleThread implements Runnable {
    private final BigInteger index;
    private final String userName;
    private final String password;

    public SimpleThread(BigInteger index, String userName, String password) {
        this.index = index;
        this.userName = userName;
        this.password = password;
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
        File output = FileExtensionKt.appendPath(FileExtensionKt.toFile(Config.OUT_PATH), userName + ".ini");
        String param = Config.PARAM;
        if (param.contains("加密账号")) {
            param = param.replaceFirst("加密账号", SafetyUtil.md5(userName));
        } else {
            param = param.replaceFirst("普通账号", userName);
        }
        if (param.contains("加密密码")) {
            param = param.replaceFirst("加密密码", SafetyUtil.md5(password));
        } else {
            param = param.replaceFirst("普通密码", password);
        }
        HashMap<String, String> headers = new HashMap<>();
        if (!Config.HEADER.isEmpty()) {
            for (String str : Config.HEADER.split("\n")) {
                String[] arg = str.split(": ");
                headers.put(arg[0], arg[1]);
            }
        }
        String body;
        if (isJson(param)) {
            body = HttpUtils.build().exePost(Config.URL, param, headers);
        } else {
            if (Config.type.equalsIgnoreCase("POST")) {
                HashMap<String, String> forms = new HashMap<>();
                for (String str : param.split("&")) {
                    String[] arg = str.split("=");
                    if (arg.length < 2) return;
                    forms.put(arg[0], arg[1]);
                }
                body = HttpUtils.build().exePost(Config.URL, forms, headers);
            } else {
                body = HttpUtils.build().exeGet(Config.URL + "?" + param, headers);
            }
        }
        String msg = index + "\t密码：" + password;
        if (isJson(body)) {
            FileExtensionKt.append(output, msg + "\t" + new JSONObject(body));
            System.out.println(msg + "\t" + new JSONObject(body));
        } else {
            if (retry < Config.RETRY) {
                retry++;
                System.out.println("第" + retry + "次重试ing\t" + msg);
                login(retry);
            } else {
                System.out.println(msg + "\t请求失败！");
            }
        }
    }


    private String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("#yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 判断是不是json
     *
     * @param param 需要判断的数据
     * @return 判断结果
     */
    private boolean isJson(String param) {
        try {
            new JSONObject(param);
            return true;
        } catch (Exception e) {
            try {
                new JSONArray(param);
                return true;
            } catch (Exception ee) {
                return false;
            }
        }
    }

}
