package com.baimsg.manual

import com.baimsg.common.Config
import com.baimsg.utils.extension.toFile
import com.baimsg.utils.extension.appendPath
import com.baimsg.utils.extension.readLines
import kotlin.jvm.JvmStatic
import java.util.HashMap

/**
 * 查看密码的类
 *
 *
 * create by baimsg 2021/12/10
 * Email 1469010683@qq.com
 */
object Search {
    @JvmStatic
    fun main(args: Array<String>) {
        for (userName in Config.userNames) {
            val output = Config.OUT_PATH.toFile().appendPath("$userName.ini")
            if (output.isFile) {
                val dics: MutableMap<String, String> = HashMap()
                println(output.name)
                for (line in output.readLines()) {
                    if (!line.startsWith("#")) {
                        val strs = line.split("\t".toRegex()).toTypedArray()
                        if (!dics.containsKey(strs[2])) {
                            dics[strs[2]] = strs[1]
                            println(line)
                        }
                    }
                }
            }
        }
    }
}