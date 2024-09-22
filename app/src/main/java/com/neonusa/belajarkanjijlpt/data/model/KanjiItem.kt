package com.neonusa.belajarkanjijlpt.data.model

data class KanjiItem (
    val id: Int,
    val kanji: String? = null,
    val mean_id: String? = null,
    val mean_en: String? = null,
    val level: String?= null
)