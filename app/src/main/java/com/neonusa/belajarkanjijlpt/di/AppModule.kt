package com.neonusa.belajarkanjijlpt.di

import androidx.room.Room
import com.neonusa.belajarkanjijlpt.data.room.KanjiDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            KanjiDatabase::class.java,
            "kanji_database"
        ).build()
    }

    single { get<KanjiDatabase>().kanjiDao() }
}