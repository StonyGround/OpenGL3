package com.kiddo.myapplication.util

import com.leer.lib.base.BaseApplication

object CommonUtils {

    fun uRes(path: String): String {
        val result = StringBuilder()
        try {
            val inputStream = BaseApplication.getApp().assets.open(path)
            var ch: Int
            val buffer = ByteArray(1024)
            while (-1 != inputStream.read(buffer).also { ch = it }) {
                result.append(String(buffer, 0, ch))
            }
        } catch (e: Exception) {
            throw IllegalStateException("path is null")
        }
        return result.toString().replace("\\r\\n".toRegex(), "\n")
    }
}