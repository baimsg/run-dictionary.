package com.baimsg.utils.extension

import com.baimsg.utils.SafetyUtil
import com.google.gson.JsonElement
import com.google.gson.JsonParser

fun String.toMd5(): String = SafetyUtil.md5(this)

fun String.validateJson(): Boolean {
    val jsonElement: JsonElement = try {
        JsonParser.parseString(this)
    } catch (e: Exception) {
        return false
    } ?: return false
    return jsonElement.isJsonObject
}