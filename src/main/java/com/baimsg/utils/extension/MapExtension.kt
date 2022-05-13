package com.baimsg.utils.extension

fun MutableMap<String, String>.putValue(oldValue: String, newValue: String) {
    forEach { (key, value) ->
        if (value == oldValue) {
            put(key, newValue)
            return@forEach
        }
    }
}