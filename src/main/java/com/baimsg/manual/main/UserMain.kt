package com.baimsg.manual.main

import com.baimsg.network.HttpUtils
import com.baimsg.thread.SimpleThreadPoolExecutor
import com.baimsg.thread.UserThread
import com.baimsg.utils.Log
import com.baimsg.utils.extension.readLines
import com.baimsg.utils.extension.toFile
import com.baimsg.utils.toBase64Str
import com.baimsg.utils.toUTF_8
import org.jsoup.Jsoup
import java.math.BigInteger
import java.util.Base64
import java.util.concurrent.TimeUnit

fun main() {
    val resource = UserThread::class.java.getResource("/user.ini")
    if (resource != null) {
        val fixedThreadPool = SimpleThreadPoolExecutor.threadPoolExecutor
        synchronized(OwnThread::class.java) {
            HttpUtils.run = true
        }
        var index = BigInteger("0")
        resource.file.toFile().readLines().forEach { userName ->
            index = index.add(BigInteger("1"))
            fixedThreadPool.execute(UserThread(index, userName))
        }
        fixedThreadPool.shutdown()
        while (!fixedThreadPool.awaitTermination(1, TimeUnit.SECONDS)) {
            Thread.sleep(800)
        }
        synchronized(OwnThread::class.java) {
            HttpUtils.run = false
        }
        Log.d("-> end")
    } else {
        Log.e("not fund /user.ini File")
    }
}