package com.baimsg.dictionary;

import com.baimsg.bean.User;
import com.baimsg.network.HttpUtils;
import com.baimsg.utils.SafetyUtil;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.UUID;

/**
 * create by baimsg 2021/11/27
 * Email 1469010683@qq.com
 * <p>
 * 有料登录实现类
 **/
public class ExpectedDictionary implements DictionarySuper {

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
//        headers.put("Host", "120.79.122.204");
    }
    public ExpectedDictionary(User user) {
        this.user = user;
    }

    @Override
    public User login() {
        try {
            JSONObject JSON_RES = new JSONObject();
            JSON_RES.put("latitude", "");
            JSON_RES.put("longitude", "");
            JSON_RES.put("device", "0");
            JSON_RES.put("deviceInfo", "Xiaomi");
            JSON_RES.put("userName", user.getPhone());
            JSON_RES.put("userPw", SafetyUtil.md5(user.getPassword()));
            JSON_RES.put("deviceID", UUID.randomUUID().toString());
            JSONObject res = new JSONObject(HttpUtils.build().
                    exePost("http://120.79.122.204/auth/login/userName",
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
            user.setCode(404);
            user.setSuccess(false);
            user.setMessage(e.getMessage());
        }
        return user;
    }
}
