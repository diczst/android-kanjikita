package com.neonusa.belajarkanjijlpt.model

data class KanjiSubitem (
    val id: Int,
    val kanji_item_id: Int,
    val kanji_word: String,
    val furigana: String,
    val romaji: String,
    val mean: String
)