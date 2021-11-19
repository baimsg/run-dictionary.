package com.baimsg.thread;

import com.baimsg.Main;

import java.util.concurrent.*;

/**
 * Create by Baimsg on 2021/11/19
 **/
public class DictionaryThreadPoolExecutor {
    public final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(Main.maxThread,
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
