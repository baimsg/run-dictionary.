package com.baimsg.thread;

import java.util.concurrent.*;

/**
 * Create by Baimsg on 2021/11/19
 **/
public class DictionaryThreadPoolExecutor {
    public final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8,
            Integer.MAX_VALUE,
            20,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            Executors.defaultThreadFactory(),
            new DictionaryPolicy()
    );

    private DictionaryThreadPoolExecutor() {
    }

}
