package com.neonusa.belajarkanjijlpt.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    // Update bookmark status
    fun updateBookmarkStatus(kanjiId: Int, isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            kanjiDao.updateCheckedStatus(kanjiId, isChecked)
        }
    }

    // Function to get 10 kanjis based on the list of IDs
    // Function to get 10 kanjis based on the list of IDs
    fun getKanjisByIds(kanjiIds: List<Int>): LiveData<List<KanjiWord>> {
        return kanjiDao.getKanjisByIds(kanjiIds) // Return LiveData directly
    }
}