package com.baimsg.dictionary;

import com.baimsg.bean.User;
import com.baimsg.network.HttpUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * create by baimsg 2021/11/28
 * Email 1469010683@qq.com
 **/
public class SignalingDictionary implements DictionarySuper {
    private final User user;
    private static final Map<String, String> headers = new HashMap<>();

    static {
        //初始化请求头
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Connection", "close");
        headers.put("charset", "UTF-8");
        headers.put("Nonce", "123456");
        headers.put("Sign", "cecd15c5378f021def04261a63a325b50ec9f7e1");
        headers.put("Appid", "2019041947866");
//        headers.put("Host", "apizdd.com");
    }

    public SignalingDictionary(User user) {
        this.user = user;
    }

    @Override
    public User login() {
        try {
            //初始化提交表单
            Map<String, String> form = new HashMap<>();
            form.put("device", "android");
            form.put("version", "21.04.27");
            form.put("account", user.getPhone());
            form.put("password", user.getPassword());

            JSONObject res = new JSONObject(HttpUtils.build().
                    exePost("https://apizdd.com/api/user/login",
                            form,
                            headers
                    ));
            String message = res.getString("message");
            if (res.getLong("code") == 200) {
                JSONObject data = res.getJSONObject("data");
                user.setSuccess(true);
                user.setUserName(data.getString("nickname"));
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
