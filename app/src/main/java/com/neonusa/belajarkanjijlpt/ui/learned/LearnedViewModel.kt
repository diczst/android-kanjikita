package com.neonusa.belajarkanjijlpt.ui.learned

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

class LearnedViewModel: ViewModel(), KoinComponent {
    private val kanjiDao: KanjiDao by inject()

    val kanjis: LiveData<List<KanjiWord>> = kanjiDao.getLearnedKanji()

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
}