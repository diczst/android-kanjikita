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

    @Query("SELECT * FROM kanji_table WHERE is_checked = 1")
    fun getLearnedKanji(): LiveData<List<KanjiWord>>

    // Update bookmark status
    @Query("UPDATE kanji_table SET is_checked = :isChecked WHERE id = :kanjiId")
    fun updateCheckedStatus(kanjiId: Int, isChecked: Boolean)

    // New function to get 10 kanji based on given ids for kanji of the day
    @Query("SELECT * FROM kanji_table WHERE id IN (:kanjiIds)")
    fun getKanjisByIds(kanjiIds: List<Int>): LiveData<List<KanjiWord>>

    @Query("SELECT COUNT(*) FROM kanji_table")
    fun getKanjisCount(): Int

    @Query("SELECT COUNT(*) FROM kanji_table WHERE is_checked = 1")
    fun getCheckedKanjisCount(): LiveData<Int>
}