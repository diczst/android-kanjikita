import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neonusa.belajarkanjijlpt.data.model.KanjiWord
import com.neonusa.belajarkanjijlpt.data.room.KanjiDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DetailViewModel : ViewModel(), KoinComponent {


    private val kanjiDao: KanjiDao by inject()

    // LiveData untuk menyimpan hasil dari database
    val kanjis: LiveData<List<KanjiWord>> = kanjiDao.getAllKanjis()

    // Memasukkan data ke dalam database
    fun insertJsonDataToDatabase(jsonString: String) {
        viewModelScope.launch {
            val kanjiList = parseJsonToKanjiList(jsonString)
            Log.d(this::class.simpleName, "insertJsonDataToDatabase: $kanjiList")
            withContext(Dispatchers.IO) {
                kanjiDao.insertAll(kanjiList) // Insert on background thread
            }
        }
    }

    // Update bookmark status
    fun updateBookmarkStatus(kanjiId: Int, isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            kanjiDao.updateCheckedStatus(kanjiId, isChecked)
        }
    }

    // Fungsi helper untuk parsing JSON (implementasikan sesuai kebutuhan Anda)
    private fun parseJsonToKanjiList(jsonString: String): List<KanjiWord> {
        return try {
            // Define the type for List<KanjiWord>
            val kanjiListType = object : TypeToken<List<KanjiWord>>() {}.type
            val kanjiList: List<KanjiWord> = Gson().fromJson(jsonString, kanjiListType)
            Log.d(this::class.simpleName, "JSON parsed successfully: $kanjiList")
            kanjiList // Return the parsed list
        } catch (e: Exception) {
            Log.e(this::class.simpleName, "Error parsing JSON: ${e.message}")
            emptyList() // Return empty list if parsing fails
        }
    }
}
