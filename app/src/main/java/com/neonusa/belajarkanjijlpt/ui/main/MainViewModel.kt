package com.neonusa.belajarkanjijlpt.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neonusa.belajarkanjijlpt.data.model.KanjiWord
import com.neonusa.belajarkanjijlpt.data.room.KanjiDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel : ViewModel(), KoinComponent {
    private val kanjiDao: KanjiDao by inject()

    val kanjisOfTheDay: LiveData<List<KanjiWord>> = kanjiDao.getKanjisOfTheDay()

    // Fungsi untuk me-refresh data jika diperlukan
    fun refreshKanjisOfTheDay() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                kanjiDao.getKanjisOfTheDay() // Mengambil 10 kanji acak
            }
        }
    }

    // Update bookmark status
    fun updateBookmarkStatus(kanjiId: Int, isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            kanjiDao.updateCheckedStatus(kanjiId, isChecked)
        }
    }
}