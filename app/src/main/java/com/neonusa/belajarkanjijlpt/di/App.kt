package com.neonusa.belajarkanjijlpt.di

import android.app.Application
import android.util.Log
import com.chibatching.kotpref.Kotpref
import com.google.android.gms.ads.MobileAds
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)
        initializeKoin()
        initializeAdmob()
    }

    // inisiasi kotlin dengan konteks aplikasi (agar bisa memanggil konteks di file module)
    private fun initializeKoin(){
        startKoin{
            androidContext(this@App)
            modules(listOf(appModule, viewModelModule))
        }
    }

    private fun initializeAdmob() {
        MobileAds.initialize(this) {
        }
    }
}