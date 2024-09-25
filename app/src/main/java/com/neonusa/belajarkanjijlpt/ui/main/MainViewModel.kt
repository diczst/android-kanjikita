package com.neonusa.belajarkanjijlpt.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neonusa.belajarkanjijlpt.data.model.KanjiWord
import com.neonusa.belajarkanjijlpt.data.room.KanjiDao
import com.neonusa.belajarkanjijlpt.utils.MyPreference
import com.neonusa.belajarkanjijlpt.utils.getTodayDate
import com.neonusa.belajarkanjijlpt.utils.parseJsonToKanjiList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel : ViewModel(), KoinComponent {
    private val kanjiDao: KanjiDao by inject()
    private var jsonKanjiWordString: String? = null

    // Memasukkan data ke dalam database
    fun insertJsonDataToDatabase(jsonString: String, onInsertComplete: () -> Unit) {
        viewModelScope.launch {
            val kanjiList = parseJsonToKanjiList(jsonString)
            Log.d(this::class.simpleName, "insertJsonDataToDatabase: $kanjiList")
            withContext(Dispatchers.IO) {
                kanjiDao.insertAll(kanjiList) // Insert on background thread
                withContext(Dispatchers.Main) {
                    onInsertComplete() // Notify that insert is complete
                }
            }
        }
    }

    // Update bookmark status
    fun updateBookmarkStatus(kanjiId: Int, isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            kanjiDao.updateCheckedStatus(kanjiId, isChecked)
        }
    }

    // Function to get the total count of kanjis
    fun getKanjiCount(callback: (Int) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val count = kanjiDao.getKanjisCount() // Suspend function
            withContext(Dispatchers.Main) {
                callback(count) // Return result on the Main thread
            }
        }
    }

    // Function to get the live count of checked kanjis
    fun getCheckedKanjiCount(): LiveData<Int> {
        return kanjiDao.getCheckedKanjisCount()
    }


    // Fungsi untuk mendapatkan 10 kanji acak
    fun getRandomKanjisOfTheDay(callback: (List<KanjiWord>) -> Unit) {
        val todayDate = getTodayDate()

        if (MyPreference.lastUpdateDate != todayDate) {
            // Jika tanggal berbeda, pilih 10 kanji acak baru
            viewModelScope.launch(Dispatchers.IO) {
                val totalKanjis = kanjiDao.getKanjisCount()
                if (totalKanjis >= 10) {
                    // Pilih 10 angka acak antara 1 dan total kanji yang tersedia
                    val randomKanjiIds = (1..totalKanjis).shuffled().take(10)

                    // Simpan kanji yang dipilih ke SharedPreferences
                    MyPreference.lastKanjiIds = randomKanjiIds.joinToString(",") // Simpan dalam format string
                    MyPreference.lastUpdateDate = todayDate

                    // Panggil query kanji
                    withContext(Dispatchers.Main) {
                        kanjiDao.getKanjisByIds(randomKanjiIds).observeForever { kanjiList ->
                            callback(kanjiList ?: emptyList())
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        callback(emptyList()) // Jika tidak ada cukup kanji
                    }
                }
            }
        } else {
            // Jika tanggal sama, gunakan ID kanji yang disimpan
            val kanjiIds = MyPreference.lastKanjiIds.split(",").map { it.toInt() }

            viewModelScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    kanjiDao.getKanjisByIds(kanjiIds).observeForever { kanjiList ->
                        callback(kanjiList ?: emptyList())
                    }
                }
            }
        }
    }
}