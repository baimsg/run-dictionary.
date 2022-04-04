package com.baimsg.thread

import com.baimsg.Main
import com.baimsg.bean.User
import com.baimsg.utils.extension.readLines
import com.baimsg.utils.extension.toFile
import java.math.BigInteger

class TaskThread(
    private val appName: String, private val userName: String
) : Runnable {
    override fun run() {
        val resource = Main::class.java.getResource("/password.ini") ?: return

        var index = BigInteger("0")
        resource.path.toFile().readLines().forEachIndexed { _, s ->
            index = index.add(BigInteger("1"))
            DictionaryThreadPoolExecutor.threadPoolExecutor.execute(
                DictionaryThread(
                    User(
                        index, appName, userName, s
                    )
                )
            )
        }

    }
}