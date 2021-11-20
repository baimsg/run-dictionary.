# Run Dictionary

## 实现了跑字典登录某app的功能！

> #### 使用第三方库以及技术
> - okhttp3
> - 线程池
> - 策略设计模式
> - 简单工厂设计模式

- # ___使用说明___

#### 01·设置线程池核心线程数（就是并发次数）

```java
    public static int maxThread=50;
 ```

#### 02·设置账号

```java
     private static final String userName="baimsg";
 ```

#### 03·选择跑字典的app

```java
    private static final String appName=KEYS.get(2);
 ```

点击[详见](https://gitee.com/baimsg/run-dictionary/blob/master/src/main/java/com/baimsg/Main.java)

### 自定义字典

#### 只需要替换 [resources](https://gitee.com/baimsg/run-dictionary/tree/master/src/main) 里面的 [password.ini](https://gitee.com/baimsg/run-dictionary/tree/master/src/main/resources) 即可

- # ___开发遇到的坑（：___
- #### 1.起初请求参数放在构造函数里面初始化，由于使用了多线程。并发次数过快时，Map的put方法和json的put方法有几率无法正常执行！这直接影响了跑字典时无法正确跑出密码，后将初始化代码放到login函数之后解决问题。

- #### 2.友聊api请求时携带Host请求头导致无法成功请求数据，这个问题目前还是不清楚具体原因，初步判断是http连接端口指向错误相关的问题。有大神知道可以提issues(⓿_⓿)
```java
//错误案例
public class FriendsChatDictionary implements DictionarySuper {
    private final User user;
    private static final HashMap<String, String> headers = new HashMap();
    private static final HashMap<String, String> form = new HashMap();

    public FriendsChatDictionary(User user) {
        this.user = user;
        form.put("account", user.getPhone());
        form.put("password", user.getPassword());
    }

    static {
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Host", "yl0528yl01.cc:51001");
        headers.put("Connection", "close");
        form.put("os", "android");
        form.put("v", "2.2.5");
    }
}


//改版代码
public class FriendsChatDictionary implements DictionarySuper {
    private final User user;
    private static final HashMap<String, String> headers = new HashMap();

    public FriendsChatDictionary(User user) {
        this.user = user;
    }

    public User login() {
        HashMap<String, String> form = new HashMap();
        form.put("os", "android");
        form.put("v", "2.2.5");
        form.put("account", user.getPhone());
        form.put("password", user.getPassword());
        return this.user;
    }

    static {
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Connection", "close");
//        headers.put("Host", "yl0528yl01.cc:51001");
    }
}
```
## 写完了(‧‧)nnn