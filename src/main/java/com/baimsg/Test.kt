package com.baimsg

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.FileInputStream
import java.nio.charset.Charset
import java.util.*

fun main() {
    val fw = FileInputStream("./src/main/resources/img_1.png")
    val bytes = ByteArray(57)
    val sb = StringBuilder()
    var byteread: Int
    while (fw.read(bytes).also { byteread = it } != -1) {
        val newByte = ByteArray(byteread)
        System.arraycopy(bytes, 0, newByte, 0, byteread)
        sb.append(newByte.encodeBase64() + "\n")
    }
    println(sb)
    val client = OkHttpClient.Builder().build()
    val request: Request = Request.Builder().url("https://aidemo.youdao.com/ocr_question").post(
        "imgBase=data:image/png;base64,${
            sb.toString().trim()
        }".toRequestBody("application/x-www-form-urlencoded; charset=utf-8".toMediaTypeOrNull())
    ).build()

    val exe = client.newCall(request).execute()
    println(exe.body?.bytes()?.to())

}

fun ByteArray.to(charset: Charset = Charsets.UTF_8): String {
    return String(this, charset)
}

fun ByteArray.encodeBase64(): String {
    return Base64.getEncoder().encode(this).to()
}