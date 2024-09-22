package com.neonusa.belajarkanjijlpt.di

import com.neonusa.belajarkanjijlpt.utils.MyPreference
import org.koin.dsl.module
import java.util.Locale

val appModule = module {
    single {
        val defaultLocale = Locale.getDefault()
        if(MyPreference.lang.equals("default")){
            if(defaultLocale.language.equals("in")){
                MyPreference.lang = "in"
            } else {
                MyPreference.lang = "en"
            }
        }
    }
}