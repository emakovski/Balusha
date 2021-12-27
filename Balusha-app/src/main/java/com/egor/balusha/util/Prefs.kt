package com.egor.balusha.util

import android.content.Context
import android.content.SharedPreferences

const val GLOBAL_PREFERENCES = "GLOBAL_PREFERENCES"

class Prefs (context: Context)
{
    private val NIGHT_THEME = "nightTheme"

    private val preferences: SharedPreferences = context.getSharedPreferences(GLOBAL_PREFERENCES, Context.MODE_PRIVATE)

    var nightTheme: String?
        get() = preferences.getString(NIGHT_THEME, "")
        set(value) = preferences.edit().putString(NIGHT_THEME, value).apply()
}