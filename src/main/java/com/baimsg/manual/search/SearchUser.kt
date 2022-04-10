package com.baimsg.manual.search

import com.baimsg.common.Config
import com.baimsg.utils.Log
import com.baimsg.utils.extension.*

fun main() {
    val users = mutableSetOf<String>()
    Config.USER_PATH.listFiles()?.forEachIndexed { _, file ->
        if (file.isFile) {
            Log.d(file.name)
            val msg = file.read() ?: ""
            if (msg.contains("nickname") || msg.contains("avatar") || msg.contains("name")) {
                Log.d("存在 -> $msg")
                users.add(file.name.split(".")[0])
            } else {
                Log.i("不存在 -> $msg")
            }
            file.delete()
        }
    }

    Log.d("以下账号判断为存在：")
    val path = Config.OUT_PATH.toFile().appendPath("hasUser.ini")
    if (path.isFile) {
        path.delete()
        Log.e("${path.name} -> 已清空")
    }
    users.forEach { userName ->
        Log.d(userName)
        path.append(userName)
    }
    Log.d("保存路径 —> $path")
}