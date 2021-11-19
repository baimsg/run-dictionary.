package com.baimsg.dictionary;

import com.baimsg.bean.User;
import com.baimsg.network.HttpUtils;
import com.baimsg.utils.SafetyUtil;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.UUID;

/**
 * Create by Baimsg on 2021/11/18
 * <p>
 * 探Mi登录实现类
 **/
public class ExploreDictionary implements DictionarySuper {
    private final User user;
    private static final HashMap<String, String> headers = new HashMap<>();
    private static final JSONObject JSON_RES = new JSONObject();

    static {
        //初始化请求头
        headers.put("Connection", "close");
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("version", "1.0.0");
        headers.put("device", "0");
        headers.put("sendTime", System.currentTimeMillis() + "");
        headers.put("lite-x-version", "register");
        headers.put("Host", "139.159.244.191:7799");

        //初始化请求参数
        JSON_RES.put("latitude", "");
        JSON_RES.put("longitude", "");
        JSON_RES.put("device", "0");
    }

    public ExploreDictionary(User user) {
        this.user = user;
        JSON_RES.put("userName", user.getPhone());
        JSON_RES.put("userPw", SafetyUtil.md5(user.getPassword()));
        JSON_RES.put("deviceID", UUID.randomUUID().toString());
    }

    @Override
    public User login() {
        try {
            JSONObject res = new JSONObject(HttpUtils.build().
                    exePost("http://139.159.244.191:7799/auth/login/userName",
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
            user.setSuccess(false);
            user.setMessage(e.getMessage());
        }
        return user;
    }


    /*

     {"code":10000,"message":"操作成功","data":{"teamName":"我的团队","isSignInRecord":0,"signInSwitch":1,"latestLoginTime":"1637219737568","allowAddFriends":0,"userPhone":null,"signInTips":1,"isOnline":null,"isPayPassword":0,"isNewMessageNotice":1,"signRule":"活动内容\n1.每日签到，由系统发放奖励。\n\n活动细则\n1.客户可以登录有消息客户端，在“发现”页面中“签到红包”，点击“签到”参与签到获得奖励；\n2.每日签到可以获得签到金；\n3.每日最多可签到1次，断签则会重新计算连签天数；\n连续签到7天奖励额外奖励18.88\n连续签到15天奖励额外奖励38.88\n连续签到28天奖励额外奖励88.88\n4.用户应充分了解本活动规则并遵照执行。如经核实用户有违规、作弊等情况，除按照相关协议及规则处理外，所获活动奖励也将被取消。","nickname":"baimsg","allowBeAddFriends":null,"whatsUp":null,"isShakeNotice":0,"userSex":0,"myTeamId":905808405292266055,"registerTime":"2021-11-18 00:45:03","userBackgroundFile":"","userDesc":null,"signContinueReward":{"15":3888,"28":9999,"7":1888},"userName":"baimsg","updateNum":0,"maxFriend":5000,"parentId":0,"token":"eyJhbGciOiJIUzUxMiJ9.eyJ1aWQiOiI4NzgyIiwic3ViIjoiMU16NE1ydWN5NWRFaWZlOGtydnNFd2FSRXRFd0RYY2dQZSIsImxvZ2luTmFtZSI6IjFNejRNcnVjeTVkRWlmZThrcnZzRXdhUkV0RXdEWGNnUGUiLCJvc1R5cGUiOiIwIiwiY2xpZW50VmVyc2lvbiI6IjEuMC4wIiwiZXhwIjoxNjM5ODExNzM3LCJkZXZpY2VJRCI6IjY4YjZiZGRlLTc3ZDctNGE3Zi05ODE3LThmZDYwYTcwNjY3MSIsImlhdCI6MTYzNzIxOTczN30.4zoXlKWuhmOJFCPFJTd7vCGf_qj36X08vw7VC3BUKY3a_r2lkKsMZ_Tsiw6eawa6OXuCihqgpMfFGjKLXyQjXA","areaCode":"86","userUuid":"IM_3fouc1VVZb3FpnwkOA7Q0y","userMail":null,"userAvatarFileName":"8782_1074680561d3d2c0505124db93612d03.jpg","userType":0,"walletAddress":"1Mz4Mrucy5dEife8krvsEwaREtEwDXcgPe","latestLoginIp":"121.69.92.142","isVoiceNotice":1,"userUid":8782}}
     {"code":40019,"message":"密码错误"}

     */

}
