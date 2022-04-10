package com.baimsg.thread

import java.util.concurrent.RejectedExecutionHandler
import java.lang.Runnable
import java.util.concurrent.ThreadPoolExecutor

/**
 * Create by Baimsg on 2021/11/19
 */
class DictionaryPolicy : RejectedExecutionHandler {
    override fun rejectedExecution(r: Runnable, executor: ThreadPoolExecutor) {
        println(r.toString() + "被拒绝执行")
        if (!executor.isShutdown) {
            r.run()
        }
    }
}