package com.neonusa.belajarkanjijlpt.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.neonusa.belajarkanjijlpt.data.model.KanjiWord

@Dao
interface KanjiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(kanjis: List<KanjiWord>)

    @Query("SELECT * FROM kanji_table")
    fun getAllKanjis(): LiveData<List<KanjiWord>>
}