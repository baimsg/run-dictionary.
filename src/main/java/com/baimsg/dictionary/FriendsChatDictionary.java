package com.baimsg.dictionary;

import com.baimsg.bean.User;
import com.baimsg.network.HttpUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by Baimsg on 2021/11/18
 * <p>
 * 友聊登录功能实现类
 **/
public class FriendsChatDictionary implements DictionarySuper {
    private final User user;
    private static final Map<String, String> headers = new HashMap<>();

    static {
        //初始化请求头
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Connection", "close");
    }

    public FriendsChatDictionary(User user) {
        this.user = user;
    }

    @Override
    public User login() {
        try {
            //初始化提交表单
            Map<String, String> form = new HashMap<>();
            form.put("os", "android");
            form.put("v", "2.2.5");
            form.put("account", user.getPhone());
            form.put("password", user.getPassword());

            JSONObject res = new JSONObject(HttpUtils.build().
                    exePost("https://yl0528yl01.cc:51001/api/user/login",
                            form,
                            headers
                    ));
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
