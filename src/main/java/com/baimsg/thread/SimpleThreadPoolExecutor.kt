package com.baimsg.thread

import com.baimsg.common.Config
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.Executors

/**
 * Create by Baimsg on 2021/11/19
 */
object SimpleThreadPoolExecutor {

    /**
     * 重写getter让每次返回对象都不不是同一个
     */
    val threadPoolExecutor
        get() = ThreadPoolExecutor(
            Config.maxThread,
            Int.MAX_VALUE,
            10,
            TimeUnit.SECONDS,
            LinkedBlockingQueue(),
            Executors.defaultThreadFactory(),
            SimplePolicy()
        )
}