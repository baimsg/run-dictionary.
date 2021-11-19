package com.baimsg.dictionary;

import com.baimsg.bean.User;
import com.baimsg.network.HttpUtils;
import com.baimsg.utils.SafetyUtil;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.UUID;

/**
 * Create by Baimsg on 2021/11/18
 * <p>
 * 探Mi登录实现类
 **/
public class ExploreDictionary implements DictionarySuper {
    private final User user;
    private static final HashMap<String, String> headers = new HashMap<>();

    static {
        //初始化请求头
        headers.put("Connection", "close");
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("version", "1.0.0");
        headers.put("device", "0");
        headers.put("sendTime", System.currentTimeMillis() + "");
        headers.put("lite-x-version", "register");
//        headers.put("Host", "139.159.244.191:7799");
    }

    public ExploreDictionary(User user) {
        this.user = user;
    }

    @Override
    public User login() {
        try {
            JSONObject JSON_RES = new JSONObject();
            JSON_RES.put("latitude", "");
            JSON_RES.put("longitude", "");
            JSON_RES.put("device", "0");
            JSON_RES.put("userName", user.getPhone());
            JSON_RES.put("userPw", SafetyUtil.md5(user.getPassword()));
            JSON_RES.put("deviceID", UUID.randomUUID().toString());
            JSONObject res = new JSONObject(HttpUtils.build().
                    exePost("http://139.159.244.191:7799/auth/login/userName",
                            JSON_RES.toString(),
                            headers
                    ));
            String message = res.getString("message");
            if (res.getLong("code") == 10000) {
                JSONObject data = res.getJSONObject("data");
                user.setSuccess(true);
                user.setUserName(data.getString("userName"));
                user.setToken(data.getString("token"));
            } else {
                user.setSuccess(false);
            }
            user.setMessage(message);
        } catch (Exception e) {
            user.setSuccess(false);
            user.setMessage(e.getMessage());
        }
        return user;
    }

}
