package com.neonusa.belajarkanjijlpt.utils

import android.content.Context
import java.io.IOException

// mengubah file json menjadi string biasa
fun loadJSONFromAssets(fileName: String, context: Context): String? {
    return try {
        val inputStream = context.assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        String(buffer, Charsets.UTF_8)
    } catch (ex: IOException) {
        ex.printStackTrace()
        null
    }
}