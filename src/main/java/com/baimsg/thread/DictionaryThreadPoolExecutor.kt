package com.baimsg.thread

import com.baimsg.common.Config
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.LinkedBlockingQueue
import java.lang.Runnable
import java.util.concurrent.Executors
import com.baimsg.thread.DictionaryPolicy

/**
 * Create by Baimsg on 2021/11/19
 */
object DictionaryThreadPoolExecutor {
    val threadPoolExecutor = ThreadPoolExecutor(
        Config.maxThread,
        Int.MAX_VALUE,
        10,
        TimeUnit.SECONDS,
        LinkedBlockingQueue(),
        Executors.defaultThreadFactory(),
        DictionaryPolicy()
    )
}