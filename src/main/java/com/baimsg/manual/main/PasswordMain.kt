package com.baimsg.manual.main

import com.baimsg.common.Config
import com.baimsg.network.HttpUtils
import com.baimsg.thread.PasswordThread
import com.baimsg.thread.SimpleThreadPoolExecutor
import com.baimsg.utils.Log
import com.baimsg.utils.extension.appendPath
import com.baimsg.utils.extension.readLines
import com.baimsg.utils.extension.toFile
import java.math.BigInteger
import java.util.concurrent.TimeUnit

/**
 * create by baimsg 2021/11/26
 * Email 1469010683@qq.com
 *
 *
 * 实现了手动测试
 */
fun main() {
    val fixedThreadPool = SimpleThreadPoolExecutor.threadPoolExecutor
    for (userName in Config.userNames) {
        Log.d("[账号:$userName] -> start")
        fixedThreadPool.execute(OwnThread(userName))
    }
    fixedThreadPool.shutdown()
    while (!fixedThreadPool.awaitTermination(1, TimeUnit.SECONDS)) {
        Thread.sleep(800)
    }
    synchronized(OwnThread::class.java) {
        HttpUtils.run = false
    }
    Log.d("-> end")
}

internal class OwnThread(private var userName: String) : Runnable {
    override fun run() {
        val threadPool = SimpleThreadPoolExecutor.threadPoolExecutor
        if (!Config.PASSWORD_PATH.exists()) {
            Log.d("路径 -> ${Config.PASSWORD_PATH} 创建${if (Config.PASSWORD_PATH.mkdirs()) "成功" else "失败"}")
        }
        val output = Config.PASSWORD_PATH.appendPath("${userName}.ini")
        if (output.isFile) {
            output.delete()
            Log.d("${output.name} -> 数据已清空！")
        }
        val resource = OwnThread::class.java.getResource("/password.ini")
        if (resource != null) {
            var index = BigInteger("0")
            resource.file.toFile().readLines().forEach { s ->
                index = index.add(BigInteger("1"))
                threadPool.execute(PasswordThread(index, userName, s))
            }
            threadPool.shutdown()
            while (!threadPool.awaitTermination(1, TimeUnit.SECONDS)) {
                Thread.sleep(800)
            }
            Log.d("[$userName] -> end")
        } else {
            Log.e("not fund /password.ini File")
        }

    }
}