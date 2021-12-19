package com.baimsg.dictionary;

import com.baimsg.bean.User;
import com.baimsg.network.HttpUtils;
import com.baimsg.utils.SafetyUtil;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 微彩聊登录逻辑实现
 */
public class MicroColorChatDictionary implements DictionarySuper {
    private final User user;
    private static final Map<String, String> headers = new HashMap<>();

    static {
        //初始化请求头
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Connection", "close");
    }

    public MicroColorChatDictionary(User user) {
        this.user = user;
    }

    @Override
    public User login() {
        try {
            //初始化提交表单
            Map<String, String> form = new HashMap<>();
            form.put("os", "android");
            form.put("v", "2.1.8");
            form.put("account", user.getPhone());
            form.put("password", user.getPassword());
            JSONObject res = new JSONObject(HttpUtils.build().exePost("https://weicailiao8.com:51001/api/user/login", form, headers));
            String message = res.getString("msg");
            if (res.getLong("code") == 0) {
                JSONObject data = res.getJSONObject("data");
                user.setSuccess(true);
                user.setUserName(data.getLong("id") + "");
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
