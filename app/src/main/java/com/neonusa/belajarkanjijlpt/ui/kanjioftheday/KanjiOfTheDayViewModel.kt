package com.neonusa.belajarkanjijlpt.ui.kanjioftheday

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neonusa.belajarkanjijlpt.data.model.KanjiWord
import com.neonusa.belajarkanjijlpt.data.room.KanjiDao
import com.neonusa.belajarkanjijlpt.utils.MyPreference
import com.neonusa.belajarkanjijlpt.utils.getTodayDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KanjiOfTheDayViewModel: ViewModel(), KoinComponent {
    private val kanjiDao: KanjiDao by inject()

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

    fun updateBookmarkStatus(kanjiId: Int, isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            kanjiDao.updateCheckedStatus(kanjiId, isChecked)
        }
    }

    // Function to get the live count of checked kanjis
    fun getCheckedKanjiOfTheDayCount(): LiveData<Int> {
        return kanjiDao.getCheckedKanjisCountByIds(MyPreference.lastKanjiIds.split(",").map { it.toInt() })
    }

}