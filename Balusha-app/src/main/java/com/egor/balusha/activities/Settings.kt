package com.egor.balusha.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.egor.balusha.activities.main.view.MainActivity
import com.egor.balusha.databinding.SettingsBinding
import com.egor.balusha.util.BalushaApplication
import com.egor.balusha.util.Prefs

class Settings: AppCompatActivity() {
    private lateinit var binding: SettingsBinding
    private val prefs: Prefs = BalushaApplication.prefs!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (prefs.nightTheme){binding.settingsMode.isChecked = true}

        binding.settingsMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                prefs.nightTheme = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                prefs.nightTheme = false
            }

        }

        binding.fabSettings.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}