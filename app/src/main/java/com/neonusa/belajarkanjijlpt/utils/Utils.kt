package com.neonusa.belajarkanjijlpt.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neonusa.belajarkanjijlpt.data.model.KanjiWord
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

// Fungsi helper untuk parsing JSON (implementasikan sesuai kebutuhan Anda)
fun parseJsonToKanjiList(jsonString: String): List<KanjiWord> {
    return try {
        // Define the type for List<KanjiWord>
        val kanjiListType = object : TypeToken<List<KanjiWord>>() {}.type
        val kanjiList: List<KanjiWord> = Gson().fromJson(jsonString, kanjiListType)
        kanjiList // Return the parsed list
    } catch (e: Exception) {
        emptyList() // Return empty list if parsing fails
    }
}

fun getTodayDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date())
}