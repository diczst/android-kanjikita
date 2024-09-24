package com.neonusa.belajarkanjijlpt.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kanji_table")
data class KanjiWord(
    @PrimaryKey val id: Int,
    val kanji_word: String,
    val furigana: String,
    val romaji: String,
    val mean_id: String,
    val mean_en: String,
    var is_checked: Boolean = false // Default value untuk bookmark
)
