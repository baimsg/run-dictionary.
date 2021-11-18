package com.baimsg.dictionary;

import com.baimsg.bean.User;
import com.baimsg.network.HttpUtils;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Create by Baimsg on 2021/11/18
 * <p>
 * 友聊登录功能实现类
 **/
public class FriendsChatDictionary implements DictionarySuper {
    private final User user;
    private static final HashMap<String, String> headers = new HashMap<>();
    private static final HashMap<String, String> form = new HashMap<>();

    static {
        //初始化请求头
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Host", "yl0528yl01.cc:51001");
        headers.put("Connection", "Keep-Alive");

        //初始化提交表单
        form.put("os", "android");
        form.put("v", "2.2.5");
    }

    public FriendsChatDictionary(User user) {
        this.user = user;
        form.put("account", user.getPhone());
        form.put("password", user.getPassword());
    }

    @Override
    public User login() {
        try {
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
            e.printStackTrace();
            user.setSuccess(false);
            user.setMessage(e.getMessage());
        }
        return user;
    }
}
