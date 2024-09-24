package com.neonusa.belajarkanjijlpt.utils

import android.content.Context
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

fun getTodayDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date())
}