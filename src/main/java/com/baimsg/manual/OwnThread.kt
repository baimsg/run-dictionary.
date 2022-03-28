package com.baimsg.manual

import com.baimsg.Config
import com.baimsg.thread.DictionaryPolicy
import com.baimsg.thread.DictionaryThreadPoolExecutor
import com.baimsg.thread.SimpleThread
import com.baimsg.utils.extension.appendPath
import com.baimsg.utils.extension.toFile
import java.io.BufferedReader
import java.io.FileReader
import java.math.BigInteger
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class OwnThread(private var userName: String) : Runnable {
    override fun run() {
        val threadPool = ThreadPoolExecutor(
            Config.maxThread,
            Integer.MAX_VALUE,
            10,
            TimeUnit.SECONDS,
            LinkedBlockingQueue(),
            Executors.defaultThreadFactory(),
            DictionaryPolicy()
        )

        val output = Config.OUT_PATH.toFile().appendPath("${userName}.ini")
        if (output.isFile) {
            output.delete()
            println("${output.path}数据已清空！")
        }

        var br: BufferedReader? = null
        val resource = Own::class.java.getResource("/password.ini")
        try {
            if (resource != null) {
                br = BufferedReader(FileReader(resource.file))
                var index = BigInteger("0")
                br.readLines().forEachIndexed { _, s ->
                    index = index.add(BigInteger("1"))
                    threadPool.execute(SimpleThread(index, userName, s))
                }
                threadPool.shutdown()
                br.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                assert(br != null)
                br!!.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}