package com.baimsg.dictionary;

import com.baimsg.bean.User;
import com.baimsg.network.HttpUtils;
import com.baimsg.utils.SafetyUtil;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * create by baimsg 2021/12/10
 * Email 1469010683@qq.com
 **/
public class GuaGuaDictionary implements DictionarySuper {

    private final User user;
    private static final Map<String, String> headers = new HashMap<>();

    static {
        //初始化请求头
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Connection", "close");
    }

    public GuaGuaDictionary(User user) {
        this.user = user;
    }

    @Override
    public User login() {
        try {
            //初始化提交表单
            Map<String, String> form = new HashMap<>();
            form.put("p_is_android", "1");
            form.put("loginname", user.getPhone());
            form.put("pd5", SafetyUtil.md5(user.getPassword()));
            JSONObject res = new JSONObject(HttpUtils.build().
                    exePost("http://www.guanguanroof1.club:9296/mytio/login.tio_x",
                            form,
                            headers
                    ));
            if (res.getBoolean("ok")) {
                user.setSuccess(true);
                user.setUserName("无昵称");
                user.setToken("无token");
            } else {
                user.setSuccess(false);
                String message = res.getString("msg");
                user.setMessage(message);
            }
        } catch (Exception e) {
            user.setCode(404);
            user.setSuccess(false);
            user.setMessage(e.getMessage());
        }
        return user;
    }
}
