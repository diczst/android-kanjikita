package com.neonusa.belajarkanjijlpt.data.model

data class KanjiItem (
    val id: Int,
    val kanji: String,
    val mean: String,

    // new
    val mean_en: String? = null,
    val JLPTLevel: String?= null
)