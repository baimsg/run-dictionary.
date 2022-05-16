package com.baimsg.utils

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.Mac
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


/**
 * create by baimsg 2021/11/17
 * Email 1469010683@qq.com
 */
object SafetyUtil {
    private val HEX_DIGITS = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

    /**
     * 获取md5字节
     */
    fun md5Bytes(byteArray: ByteArray): ByteArray {
        return try {
            val md = MessageDigest.getInstance("MD5")
            md.update(byteArray)
            md.digest()
        } catch (e: Exception) {
            e.printStackTrace()
            ByteArray(0)
        }
    }

    /**
     * md5加密
     */
    fun md5(str: String): String {
        return try {
            bytesToHexString(md5Bytes(str.toByteArray()))
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun bytesToHexString(input: ByteArray?): String {
        requireNotNull(input) { "input should not be null" }
        val builder = StringBuilder(input.size * 2)
        for (b in input) {
            builder.append(HEX_DIGITS[b.toInt() ushr 4 and 0xf])
            builder.append(HEX_DIGITS[b.toInt() and 0xf])
        }
        return builder.toString()
    }

    /**
     * SHA加密
     * "a060e77c5a8485aeb061aee5d7f8fa709acb4473" + "123456"
     *
     * @param str 加密内容
     * @return 密文
     */
    fun shaEncode(str: String): String {
        return try {
            val digest = MessageDigest.getInstance("SHA").digest(str.toByteArray(StandardCharsets.UTF_8))
            val stringBuffer = StringBuilder()
            for (b in digest) {
                if (b.toInt() < 16) {
                    stringBuffer.append(0)
                }
                stringBuffer.append(Integer.toHexString(b.toInt()))
            }
            stringBuffer.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            e.printStackTrace()
            ""
        }
    }


    private val passIv = byteArrayOf(
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16
    )

    fun passWordAes(data: ByteArray?, keys: ByteArray): ByteArray {
        var key = keys
        return try {
            if (key.size != 16) {
                key = keys.copyOfRange(0, 16)
            }
            KeyGenerator.getInstance("AES")
            val secretKeySpec = SecretKeySpec(key, "AES")
            val instance = Cipher.getInstance("AES/CBC/PKCS5Padding")
            instance.init(1, secretKeySpec, IvParameterSpec(passIv))
            instance.doFinal(data)
        } catch (e: java.lang.Exception) {
            ByteArray(0)
        }
    }
    fun passWordAesDecode(data: ByteArray?, keys: ByteArray): ByteArray {
        var key = keys
        return try {
            if (key.size != 16) {
                key = keys.copyOfRange(0, 16)
            }
            KeyGenerator.getInstance("AES")
            val secretKeySpec = SecretKeySpec(key, "AES")
            val instance = Cipher.getInstance("AES/CBC/PKCS5Padding")
            instance.init(2, secretKeySpec, IvParameterSpec(passIv))
            instance.doFinal(data)
        } catch (e: java.lang.Exception) {
            ByteArray(0)
        }
    }

    fun macMd5(bArr: ByteArray?, bArr2: ByteArray?): ByteArray {
        return try {
            val secretKeySpec = SecretKeySpec(bArr2, "HmacMD5")
            val instance = Mac.getInstance(secretKeySpec.algorithm)
            instance.init(secretKeySpec)
            instance.doFinal(bArr)
        } catch (e: java.lang.Exception) {
            ByteArray(0)
        }
    }

}


fun ByteArray.toBase64Str(): String = Base64.getEncoder().encode(this).toUTF_8()


fun String.base64ToStr(): String = toByteArray().base64ToStr()

fun ByteArray.base64ToStr(): String = base64ToBytes().toUTF_8()

fun String.base64ToBytes(): ByteArray = toByteArray().base64ToBytes()

fun ByteArray.base64ToBytes(): ByteArray = Base64.getDecoder().decode(this)

fun ByteArray.toUTF_8() = String(this)