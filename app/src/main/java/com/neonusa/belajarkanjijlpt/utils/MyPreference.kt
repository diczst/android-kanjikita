package com.neonusa.belajarkanjijlpt.utils

import com.chibatching.kotpref.KotprefModel

object MyPreference: KotprefModel() {
    var lang by stringPref("default")

    // note : check di/appModule.kt to understand this value
}

