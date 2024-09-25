package com.neonusa.belajarkanjijlpt.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neonusa.belajarkanjijlpt.data.model.KanjiWord
import com.neonusa.belajarkanjijlpt.data.room.KanjiDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchViewModel: ViewModel(), KoinComponent {
    private val kanjiDao: KanjiDao by inject()

    // Gunakan MediatorLiveData untuk memonitor perubahan dari berbagai sumber
    private val _searchResults = MediatorLiveData<List<KanjiWord>>()
    val searchResults: LiveData<List<KanjiWord>> = _searchResults

    private var currentSource: LiveData<List<KanjiWord>>? = null

    fun searchKanji(query: String) {
        // Format query
        val formattedQuery = "%$query%"

        // LiveData dari pencarian yang baru
        val newSource = kanjiDao.searchKanji(formattedQuery)

        // Hapus source lama jika ada
        currentSource?.let {
            _searchResults.removeSource(it)
        }

        // Tambahkan source baru dan amati hasil pencarian
        _searchResults.addSource(newSource) { result ->
            _searchResults.value = result
        }

        // Simpan source saat ini untuk referensi berikutnya
        currentSource = newSource
    }

    // Fungsi untuk memperbarui status bookmark
    fun updateBookmarkStatus(kanjiId: Int, isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            kanjiDao.updateCheckedStatus(kanjiId, isChecked)
        }
    }
}