package com.baimsg.dictionary;

import com.baimsg.bean.User;

/**
 * Create by Baimsg on 2021/11/18
 * <p>
 * 策略模式工厂类
 **/
public class DictionaryContext {

    private final DictionarySuper ds;

    public DictionaryContext(User user) {
        switch (user.getChannel()) {
            case "酷聊":
                ds = new CoolChatDictionary(user);
                break;
            case "梦想":
                ds = new DreamDictionary(user);
                break;
            case "名信":
                ds = new NameLetterDictionary(user);
                break;
            case "探Mi":
            default:
                ds = new ExploreDictionary(user);
                break;
        }
    }

    public User login() {
        return ds.login();
    }
}
