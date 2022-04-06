package com.example.androidcolorpicker.util

import android.graphics.Color
import android.view.View
import com.example.androidcolorpicker.util.Constants


fun View.setCustomBackGroundColor(input:String= Constants.TEAL_COLOR_CODE, colorCode:Int?= null){
    var first:Int = colorCode ?: Color.parseColor(input)
    setBackgroundColor(Color.rgb(Color.red(first), Color.green(first), Color.blue(first)))
}