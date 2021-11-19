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

## 写完了（: