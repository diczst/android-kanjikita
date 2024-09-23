package com.neonusa.belajarkanjijlpt.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.neonusa.belajarkanjijlpt.data.model.KanjiWord

@Database(entities = [KanjiWord::class], version = 1, exportSchema = false)
abstract class KanjiDatabase : RoomDatabase() {
    abstract fun kanjiDao(): KanjiDao
}