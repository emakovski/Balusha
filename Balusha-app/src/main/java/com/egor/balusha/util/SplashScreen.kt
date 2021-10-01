package com.egor.balusha.util

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.R
import com.egor.balusha.activities.MainActivity

class SplashScreen : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_splash_screen)
            Handler().postDelayed({
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, SPLASH_TIMEOUT)
        }

        companion object {
            private const val SPLASH_TIMEOUT: Long = 1000
        }
    }
