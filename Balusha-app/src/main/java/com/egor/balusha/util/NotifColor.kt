package com.egor.balusha.util

import com.egor.balusha.R

object NotifColor {
    fun getColorInt(color: String): Int {
        return when (color) {
            BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_green)
            -> BalushaApplication.getApplicationInstance().resources.getColor(R.color.note_green_200)
            BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_blue)
            -> BalushaApplication.getApplicationInstance().resources.getColor(R.color.note_blue_200)
            BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_purple)
            -> BalushaApplication.getApplicationInstance().resources.getColor(R.color.note_purple_200)
            BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_red)
            -> BalushaApplication.getApplicationInstance().resources.getColor(R.color.note_red_200)
            BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_teal)
            -> BalushaApplication.getApplicationInstance().resources.getColor(R.color.note_teal_200)
            BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_orange)
            -> BalushaApplication.getApplicationInstance().resources.getColor(R.color.note_orange_200)
            else -> BalushaApplication.getApplicationInstance().resources.getColor(R.color.note_green_200)
        }
    }
}