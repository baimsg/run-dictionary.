package com.baimsg.manual.search

import com.baimsg.common.Config
import com.baimsg.utils.Log
import com.baimsg.utils.extension.appendPath
import com.baimsg.utils.extension.readLines

/**
 * 查看密码的类
 *
 *
 * create by baimsg 2021/12/10
 * Email 1469010683@qq.com
 */
fun main() {
    for (userName in Config.userNames) {
        val output = Config.PASSWORD_PATH.appendPath("$userName.ini")
        if (output.isFile) {
            val filterMap: MutableMap<String, String> = HashMap()
            Log.d(output.name)
            for (line in output.readLines()) {
                if (!line.startsWith("#")) {
                    val strings = line.split("\t")
                    if (!filterMap.containsKey(strings.last())) {
                        filterMap[strings.last()] = strings[1]
                        Log.i(line)
                    }
                }
            }
        }
    }
}