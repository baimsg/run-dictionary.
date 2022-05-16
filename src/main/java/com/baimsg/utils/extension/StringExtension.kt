package com.baimsg.utils.extension

import com.baimsg.common.Config
import com.baimsg.utils.SafetyUtil
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import java.util.*

fun String.toMd5(): String = SafetyUtil.md5(this)

fun String.validateJson(): Boolean {
    val jsonElement: JsonElement = try {
        JsonParser.parseString(this)
    } catch (e: Exception) {
        return false
    } ?: return false
    return jsonElement.isJsonObject
}

fun Long.verify(): String {
    val concat = Config.START_KEY + Config.END_KEY
    val substring = concat.substring(8, concat.length - 8)
    return "$this$substring".toMd5().lowercase(Locale.getDefault())
}