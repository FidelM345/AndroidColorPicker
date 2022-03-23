package com.example.androidcolorpicker.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.androidcolorpicker.R
import com.example.androidcolorpicker.setCustomBackGroundColor
import com.example.androidcolorpicker.util.Constants
import com.github.antonpopoff.colorwheel.ColorWheel

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var colorWheel:ColorWheel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        colorWheel = findViewById(R.id.colorWheel)
        val tealBtn = findViewById<Button>(R.id.teal_btn)
        val greenBtn = findViewById<Button>(R.id.green_btn)
        val orangeBtn = findViewById<Button>(R.id.orange_btn)
        tealBtn.setOnClickListener(this)
        greenBtn.setOnClickListener(this)
        orangeBtn.setOnClickListener(this)

        val tealBg = findViewById<LinearLayoutCompat>(R.id.teal_bg)
        val greenBg = findViewById<LinearLayoutCompat>(R.id.green_bg)
        val orangeBg = findViewById<LinearLayoutCompat>(R.id.orange_bg)

        tealBtn.setCustomBackGroundColor(Constants.TEAL_COLOR_CODE)
        greenBtn.setCustomBackGroundColor(Constants.GREEN_COLOR_CODE)
        orangeBtn.setCustomBackGroundColor(Constants.ORANGE_COLOR_CODE)

        changeSegmentControlColorUsingColorWheel(tealBtn, greenBtn, orangeBtn)
        controlBackGroundColorForSelectedSegmentControl(greenBg, orangeBg, tealBg)
        listenToColorChnagesFromTheColorWheel()
    }

    private fun listenToColorChnagesFromTheColorWheel() {
        colorWheel.colorChangeListener = { rgb: Int ->

            viewModel.getColorValueFromWheel(rgb)
        }
    }

    private fun controlBackGroundColorForSelectedSegmentControl(
        greenBg: LinearLayoutCompat,
        orangeBg: LinearLayoutCompat,
        tealBg: LinearLayoutCompat
    ) {
        viewModel.changeBackgroundColorForSelectedSegmentControl.observe(this) {
            val color: Int = resources.getColor(R.color.control_segment_background, null)

            if (it.green)
                greenBg.setCustomBackGroundColor(Constants.SELECTED_CONTROLLER_BG_COLOR)
            else
                greenBg.setCustomBackGroundColor(colorCode = color)


            if (it.orange)
                orangeBg.setCustomBackGroundColor(Constants.SELECTED_CONTROLLER_BG_COLOR)
            else
                orangeBg.setCustomBackGroundColor(colorCode = color)

            if (it.teal)
                tealBg.setCustomBackGroundColor(Constants.SELECTED_CONTROLLER_BG_COLOR)
            else
                tealBg.setCustomBackGroundColor(colorCode = color)
        }
    }

    private fun changeSegmentControlColorUsingColorWheel(
        tealBtn: Button,
        greenBtn: Button,
        orangeBtn: Button
    ) {
        viewModel.colorValueFromWheel.observe(this) {
            it?.let {
                viewModel.segmentedControlColorUIState!!.isDefaultColorValues = false
                if (it.teal) {
                    tealBtn.setCustomBackGroundColor(colorCode = it.colorCode)
                    viewModel.segmentedControlColorUIState.tealControl = it.colorCode!!
                }

                if (it.green) {
                    greenBtn.setCustomBackGroundColor(colorCode = it.colorCode)
                    viewModel.segmentedControlColorUIState.greenControl = it.colorCode!!
                }
                if (it.orange) {
                    orangeBtn.setCustomBackGroundColor(colorCode = it.colorCode)
                    viewModel.segmentedControlColorUIState.orangeControl = it.colorCode!!
                }

            }

        }
    }

    override fun onClick(v: View?) {
        v?.let {
            val viewId = v.id

            if (viewId == R.id.teal_btn) {
                val tealBtnColorCode =  viewModel.segmentedControlColorUIState.tealControl
                colorWheel.rgb = convertColorCodeToRGB(tealBtnColorCode)

                changeSegmentControlClickState(
                    teal = true
                )
            }
            if (viewId == R.id.green_btn) {
                val greenBtnColorCode =  viewModel.segmentedControlColorUIState.greenControl
                colorWheel.rgb = convertColorCodeToRGB(greenBtnColorCode)

                changeSegmentControlClickState(
                    green = true
                )
            }
            if (viewId == R.id.orange_btn) {

                val orangeBtnColorCode =  viewModel.segmentedControlColorUIState.orangeControl
                colorWheel.rgb = convertColorCodeToRGB(orangeBtnColorCode)

                changeSegmentControlClickState(
                    orange = true
                )
            }
        }

    }

    private fun convertColorCodeToRGB(tealBtnColorCode: Int) = Color.rgb(
        Color.red(tealBtnColorCode),
        Color.green(tealBtnColorCode),
        Color.blue(tealBtnColorCode)
    )
    private fun changeSegmentControlClickState(
        teal: Boolean = false,
        green: Boolean = false,
        orange: Boolean = false,

    ) {
        viewModel.changeSegmentControllerClickState(
            ThreeWaySegmentControlUIState(
                teal = teal,
                green = green,
                orange = orange,
            )
        )
    }
}