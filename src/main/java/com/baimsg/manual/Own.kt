package com.baimsg.manual

import com.baimsg.common.Config
import kotlin.jvm.JvmStatic
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import com.baimsg.manual.OwnThread

/**
 * create by baimsg 2021/11/26
 * Email 1469010683@qq.com
 *
 *
 * 实现了手动测试
 */
fun main() {
    val fixedThreadPool = Executors.newFixedThreadPool(Config.maxThread)
    for (userName in Config.userNames) {
        fixedThreadPool.execute(OwnThread(userName))
    }
    fixedThreadPool.shutdown()
}
