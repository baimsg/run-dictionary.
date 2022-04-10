package com.baimsg.utils.extension

import java.io.*

fun String.toFile() = File(this)

fun File.appendPath(folder: String) = File(this.path + File.separator + folder)

fun File.append(obj: Any) {
    this.write(obj, true)
}

fun File.clearAll() {
    this.write("", false)
}

fun File.write(obj: Any, isAppend: Boolean = false) {
    var fw: FileWriter? = null
    var bw: BufferedWriter? = null
    try {
        fw = FileWriter(this, isAppend)
        bw = BufferedWriter(fw)
        bw.write(obj.toString())
        bw.newLine()
        bw.flush()
        fw.flush()
    } catch (_: IOException) {
    } finally {
        bw?.close()
        fw?.close()
    }
}

fun File.readLines(): List<String> {
    var fr: FileReader? = null
    var br: BufferedReader? = null
    return try {
        fr = FileReader(this)
        br = BufferedReader(fr)
        br.readLines()
    } catch (e: IOException) {
        listOf()
    } finally {
        br?.close()
        fr?.close()
    }
}

fun File.read(): String? {
    var fr: FileReader? = null
    var br: BufferedReader? = null
    return try {
        fr = FileReader(this)
        br = BufferedReader(fr)
        br.readText()
    } catch (e: IOException) {
        null
    } finally {
        br?.close()
        fr?.close()
    }
}