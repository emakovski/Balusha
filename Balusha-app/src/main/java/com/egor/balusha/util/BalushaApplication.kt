package com.egor.balusha.util

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class BalushaApplication : Application() {

    companion object {
        var INSTANCE: BalushaApplication? = null

        fun getApplicationInstance(): BalushaApplication {
            return INSTANCE as BalushaApplication
        }

        var prefs: Prefs? = null
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        prefs = Prefs(applicationContext)
        if (prefs?.nightTheme == "yes"){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}