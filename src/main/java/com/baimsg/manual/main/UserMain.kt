package com.baimsg.manual.main

import com.baimsg.thread.SimpleThreadPoolExecutor
import com.baimsg.thread.UserThread
import com.baimsg.utils.Log
import com.baimsg.utils.extension.readLines
import com.baimsg.utils.extension.toFile
import java.math.BigInteger

fun main() {
    val resource = UserThread::class.java.getResource("/user.ini")
    if (resource != null) {
        val fixedThreadPool = SimpleThreadPoolExecutor.threadPoolExecutor
        var index = BigInteger("0")
        resource.file.toFile().readLines().forEach { userName ->
            index = index.add(BigInteger("1"))
            fixedThreadPool.execute(UserThread(index, userName))
        }
        fixedThreadPool.shutdown()
    } else {
        Log.e("not fund /user.ini File")
    }
}