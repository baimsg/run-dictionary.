package com.baimsg.thread;

import com.baimsg.Config;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Create by Baimsg on 2021/11/19
 **/
public class DictionaryThreadPoolExecutor {
    public final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(Config.maxThread,
            Integer.MAX_VALUE,
            10,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            Executors.defaultThreadFactory(),
            new DictionaryPolicy()
    );

    private DictionaryThreadPoolExecutor() {
    }

}
