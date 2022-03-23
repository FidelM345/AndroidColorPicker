package com.example.androidcolorpicker.util

import android.graphics.Color

object Constants{
    const val TEAL_COLOR_CODE = "#00c2a3"
    const val GREEN_COLOR_CODE = "#4ba54f"
    const val  ORANGE_COLOR_CODE = "#ff6100"
    const val SELECTED_CONTROLLER_BG_COLOR = "#3b3b3b"

    fun convertColorCodeToInt(input:String):Int{
       return Color.parseColor(input)
    }
}