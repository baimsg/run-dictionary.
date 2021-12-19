package com.baimsg

import java.io.FileReader
import java.nio.charset.Charset
import java.util.*

fun main() {
    val fw = FileReader("./src/main/resources/body.txt")
    val bytes = ByteArray(57)
    val sb = StringBuilder()
    var size = 0
    var index = 0
    var byteread = 0
//    while (fw.read(bytes).also { byteread = it } != -1) {
////        println(bytes.to())
//        sb.append(String(bytes, 0, byteread).toByteArray().encodeBase64()+"\n")
//    }
    fw.readLines().forEach {
        println("编码："+it.length)
        println("原码："+Base64.getMimeDecoder().decode(it).size)
    }
    println()
//    val con = Jsoup.connect("https://aidemo.youdao.com/ocr_question")
//        con. ignoreContentType (true)
//        con.requestBody("imgBase=data:image/png;base64,${fw.readAllBytes().to()}")
//    println(
//        con.post().body().text()
//    )
//    val client = OkHttpClient.Builder().build()
//    val request: Request = Request.Builder().url("https://aidemo.youdao.com/ocr_question")
//        .post(fw.readAllBytes().to().toRequestBody())
//        .build()
//
//    val exe = client.newCall(request).execute()
//    println(exe.body?.bytes()?.to())

}

fun ByteArray.to(charset: Charset = Charsets.UTF_8): String {
    return String(this, charset)
}

fun ByteArray.encodeBase64(): String {
    return Base64.getEncoder().encode(this).to()
}