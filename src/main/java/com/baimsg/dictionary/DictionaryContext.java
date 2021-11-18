package com.baimsg.dictionary;

import com.baimsg.bean.User;

/**
 * Create by Baimsg on 2021/11/18
 **/
public class DictionaryContext {

    private DictionarySuper ds;

    public DictionaryContext(User user) {
        switch (user.getChannel()) {
            case "探Mi":
            default:
                //探Mi
                ds = new ExploreDictionary(user);
                break;
        }
    }

    public User login() {
        return ds.login();
    }
}
