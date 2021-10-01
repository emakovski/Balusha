package com.egor.balusha.util

import android.app.Application

class BalushaApplication : Application() {

    companion object {
        var INSTANCE: BalushaApplication? = null

        fun getApplicationInstance(): BalushaApplication {
            return INSTANCE as BalushaApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}