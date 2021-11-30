package com.egor.balusha.activities

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.egor.balusha.databinding.SettingsBinding

private const val RESULT_CODE_BUTTON_BACK = 3

class Settings: AppCompatActivity() {
    private lateinit var binding: SettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.settingsMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
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