package com.neonusa.belajarkanjijlpt.utils

import com.chibatching.kotpref.KotprefModel

object MyPreference: KotprefModel() {
    var lang by stringPref("default")
    // note : check di/AppModule.kt to understand this value
}

