package com.baimsg.dictionary;

import com.baimsg.bean.User;
import com.baimsg.network.HttpUtils;
import com.baimsg.utils.SafetyUtil;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Create by Baimsg on 2021/11/18
 * <p>
 * 名信登录实现类
 **/
public class NameLetterDictionary implements DictionarySuper {
    private final User user;
    private static final HashMap<String, String> headers = new HashMap<>();

    static {
        //初始化请求头
        headers.put("Connection", "close");
        headers.put("Content-Type", "application/json;charset=utf-8");
//        headers.put("Host", "gateway.lx3836.com");
    }

    public NameLetterDictionary(User user) {
        this.user = user;
    }

    @Override
    public User login() {
        try {
            JSONObject JSON_RES = new JSONObject();
            JSON_RES.put("username", user.getPhone());
            JSON_RES.put("pwd", SafetyUtil.md5(user.getPassword()));
            JSONObject res = new JSONObject(HttpUtils.build().
                    exePost("http://gateway.lx3836.com/api/im/imuser/login",
                            JSON_RES.toString(),
                            headers
                    ));
            String message = res.getString("msg");
            if (res.getLong("code") == 200) {
                JSONObject data = res.getJSONObject("data");
                user.setSuccess(true);
                user.setUserName(data.getString("userId"));
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
