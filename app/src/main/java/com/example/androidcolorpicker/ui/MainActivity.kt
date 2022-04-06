package com.example.androidcolorpicker.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.androidcolorpicker.R
import com.example.androidcolorpicker.util.setCustomBackGroundColor
import com.example.androidcolorpicker.util.Constants
import com.github.antonpopoff.colorwheel.ColorWheel

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var colorWheel: ColorWheel

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

        tealBtn.setCustomBackGroundColor(colorCode = viewModel.segmentedControlColorUIState.tealControl)
        greenBtn.setCustomBackGroundColor(colorCode = viewModel.segmentedControlColorUIState.greenControl)
        orangeBtn.setCustomBackGroundColor(colorCode = viewModel.segmentedControlColorUIState.orangeControl)

        changeSegmentControlColorUsingColorWheel(tealBtn, greenBtn, orangeBtn)
        controlBackGroundColorForSelectedSegmentControl(greenBg, orangeBg, tealBg)
        listenToColorChangesFromTheColorWheel()
    }

    private fun listenToColorChangesFromTheColorWheel() {
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
                changeSegmentControlClickState(
                    teal = true
                )
                setPointerOnColorWheel(viewModel.segmentedControlColorUIState.tealControl)

            }
            if (viewId == R.id.green_btn) {

                changeSegmentControlClickState(
                    green = true
                )

                setPointerOnColorWheel(viewModel.segmentedControlColorUIState.greenControl)

            }
            if (viewId == R.id.orange_btn) {

                changeSegmentControlClickState(
                    orange = true
                )
                setPointerOnColorWheel(viewModel.segmentedControlColorUIState.orangeControl)
            }
        }

    }

    private fun setPointerOnColorWheel(colorCode:Int) {
        colorWheel.rgb = convertColorCodeToRGB(colorCode)
    }

    private fun convertColorCodeToRGB(colorCode: Int) = Color.rgb(
        Color.red(colorCode),
        Color.green(colorCode),
        Color.blue(colorCode)
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