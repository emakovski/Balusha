package com.egor.balusha.util

import androidx.core.content.ContextCompat
import com.egor.balusha.R

object NotifColor {
    fun getColorInt(color: String): Int {
        return when (color) {
                BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_green)
                -> ContextCompat.getColor(
                    BalushaApplication.getApplicationInstance(),
                    R.color.note_green_200
                )
                BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_blue)
                -> ContextCompat.getColor(
                    BalushaApplication.getApplicationInstance(),
                    R.color.note_blue_200
                )
                BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_purple)
                -> ContextCompat.getColor(
                    BalushaApplication.getApplicationInstance(),
                    R.color.note_purple_200
                )
                BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_red)
                -> ContextCompat.getColor(
                    BalushaApplication.getApplicationInstance(),
                    R.color.note_red_200
                )
                BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_teal)
                -> ContextCompat.getColor(
                    BalushaApplication.getApplicationInstance(),
                    R.color.note_teal_200
                )
                BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_orange)
                -> ContextCompat.getColor(
                    BalushaApplication.getApplicationInstance(),
                    R.color.note_orange_200
                )
                else -> ContextCompat.getColor(
                    BalushaApplication.getApplicationInstance(),
                    R.color.note_green_200
                )
//        val prefs: Prefs = BalushaApplication.prefs!!
//        if (!prefs.nightTheme){
//            return when (color) {
//                BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_green)
//                -> ContextCompat.getColor(
//                    BalushaApplication.getApplicationInstance(),
//                    R.color.note_green_200
//                )
//                BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_blue)
//                -> ContextCompat.getColor(
//                    BalushaApplication.getApplicationInstance(),
//                    R.color.note_blue_200
//                )
//                BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_purple)
//                -> ContextCompat.getColor(
//                    BalushaApplication.getApplicationInstance(),
//                    R.color.note_purple_200
//                )
//                BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_red)
//                -> ContextCompat.getColor(
//                    BalushaApplication.getApplicationInstance(),
//                    R.color.note_red_200
//                )
//                BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_teal)
//                -> ContextCompat.getColor(
//                    BalushaApplication.getApplicationInstance(),
//                    R.color.note_teal_200
//                )
//                BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_orange)
//                -> ContextCompat.getColor(
//                    BalushaApplication.getApplicationInstance(),
//                    R.color.note_orange_200
//                )
//                else -> ContextCompat.getColor(
//                    BalushaApplication.getApplicationInstance(),
//                    R.color.note_green_200
//                )
//            }
//        }else {
//            return when (color) {
//                BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_green)
//                -> ContextCompat.getColor(
//                    BalushaApplication.getApplicationInstance(),
//                    R.color.note_green_night
//                )
//                BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_blue)
//                -> ContextCompat.getColor(
//                    BalushaApplication.getApplicationInstance(),
//                    R.color.note_blue_night
//                )
//                BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_purple)
//                -> ContextCompat.getColor(
//                    BalushaApplication.getApplicationInstance(),
//                    R.color.note_purple_night
//                )
//                BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_red)
//                -> ContextCompat.getColor(
//                    BalushaApplication.getApplicationInstance(),
//                    R.color.note_red_night
//                )
//                BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_teal)
//                -> ContextCompat.getColor(
//                    BalushaApplication.getApplicationInstance(),
//                    R.color.note_teal_night
//                )
//                BalushaApplication.getApplicationInstance().resources.getString(R.string.note_color_orange)
//                -> ContextCompat.getColor(
//                    BalushaApplication.getApplicationInstance(),
//                    R.color.note_orange_night
//                )
//                else -> ContextCompat.getColor(
//                    BalushaApplication.getApplicationInstance(),
//                    R.color.note_green_night
//                )
//            }
        }
    }
}