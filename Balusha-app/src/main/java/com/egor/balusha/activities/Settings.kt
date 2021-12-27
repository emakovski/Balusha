package com.egor.balusha.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.egor.balusha.databinding.SettingsBinding
import com.egor.balusha.util.BalushaApplication
import com.egor.balusha.util.Prefs

private const val RESULT_CODE_BUTTON_BACK = 3

class Settings: AppCompatActivity() {
    private lateinit var binding: SettingsBinding
    private val prefs: Prefs = BalushaApplication.prefs!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (prefs.nightTheme == "yes"){binding.settingsMode.isChecked = true}

        binding.settingsMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                prefs.nightTheme = "yes"
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                prefs.nightTheme = "no"
            }

        }

        binding.fabSettings.setOnClickListener {
            backToPreviousActivity()
        }
    }

    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }
}