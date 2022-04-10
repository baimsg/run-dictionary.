package com.baimsg.utils

import com.baimsg.common.Constant

/**
 * Create by Baimsg on 2022/3/21
 *
 **/
object Log {
    @Volatile
    private var level = Constant.INFO
    fun i(message: CharSequence) {
        if (level == Constant.INFO) println("\u001b[0;2m$message")
    }

    fun d(message: CharSequence) {
        if (level == Constant.INFO || level == Constant.DEBUG) {
            println("\u001b[36;2m$message")
            print("\u001b[0;2m")
        }
    }

    fun e(message: CharSequence) {
        if (level == Constant.INFO || level == Constant.ERROR) {
            println("\u001b[31;2m$message")
            print("\u001b[0;2m")
        }
    }

    fun setLevel(level: Int) {
        require(!(level != Constant.NONE && level != Constant.INFO && level != Constant.DEBUG && level != Constant.ERROR)) { "日志参数信息设置错误！" }
        Log.level = level
    }

    fun getLevel(): Int {
        return level
    }
}
