package com.baimsg.dictionary;

import com.baimsg.bean.User;
import com.baimsg.network.HttpUtils;
import com.baimsg.utils.SafetyUtil;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Create by Baimsg on 2021/11/18
 * <p>
 * 酷聊登录实现类
 **/
public class CoolChatDictionary implements DictionarySuper {
    private final User user;
    private static final HashMap<String, String> headers = new HashMap<>();
    private static final JSONObject JSON_RES = new JSONObject();

    static {
        //初始化请求头
        headers.put("Connection", "close");
        headers.put("Content-Type", "application/json;charset=utf-8");
        headers.put("Host", "liaotianshi2022.com");
    }

    public CoolChatDictionary(User user) {
        this.user = user;
        JSON_RES.put("username", user.getPhone());
        JSON_RES.put("pwd", SafetyUtil.md5(user.getPassword()));
    }

    @Override
    public User login() {
        try {
            JSONObject res = new JSONObject(HttpUtils.build().
                    exePost("https://liaotianshi2022.com/api/im/imuser/login",
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
            user.setSuccess(false);
            user.setMessage(e.getMessage());
        }
        return user;
    }
}
