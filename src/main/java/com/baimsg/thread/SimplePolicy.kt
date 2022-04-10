package com.baimsg.thread

import com.baimsg.utils.Log
import java.util.concurrent.RejectedExecutionHandler
import java.lang.Runnable
import java.util.concurrent.ThreadPoolExecutor

/**
 * Create by Baimsg on 2021/11/19
 */
class SimplePolicy : RejectedExecutionHandler {
    override fun rejectedExecution(r: Runnable, executor: ThreadPoolExecutor) {
        Log.e("$r  -> 被拒绝执行")
        if (!executor.isShutdown) {
            r.run()
        }
    }
}