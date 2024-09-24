package com.neonusa.belajarkanjijlpt.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.neonusa.belajarkanjijlpt.data.model.KanjiWord

@Dao
interface KanjiDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(kanjis: List<KanjiWord>)

    @Query("SELECT * FROM kanji_table")
    fun getAllKanjis(): LiveData<List<KanjiWord>>

    // Get 10 random kanjis
    @Query("SELECT * FROM kanji_table ORDER BY RANDOM() LIMIT 10")
    fun getKanjisOfTheDay(): LiveData<List<KanjiWord>>

    // Update bookmark status
    @Query("UPDATE kanji_table SET is_checked = :isChecked WHERE id = :kanjiId")
    fun updateCheckedStatus(kanjiId: Int, isChecked: Boolean)
}