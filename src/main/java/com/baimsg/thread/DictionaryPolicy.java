package com.baimsg.thread;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Create by Baimsg on 2021/11/19
 **/
public class DictionaryPolicy implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println(r+ "被拒绝执行");
        if (!executor.isShutdown()) {
            r.run();
        }
    }
}
