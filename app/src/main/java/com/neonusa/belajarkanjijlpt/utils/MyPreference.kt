package com.neonusa.belajarkanjijlpt.utils

import com.chibatching.kotpref.KotprefModel

object MyPreference: KotprefModel() {
    var lang by stringPref("default")
    var lastUpdateDate by stringPref("") // Simpan tanggal terakhir kanji diupdate
    var lastKanjiIds by stringPref("") // Simpan list kanji terakhir dalam format string
}

