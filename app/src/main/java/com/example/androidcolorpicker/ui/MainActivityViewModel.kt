package com.example.androidcolorpicker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.androidcolorpicker.util.Constants

class MainActivityViewModel : ViewModel() {
    private val _changeBackgroundColorForSelectedSegmentControl: MutableLiveData<ThreeWaySegmentControlUIState> =
        MutableLiveData()
    val changeBackgroundColorForSelectedSegmentControl: LiveData<ThreeWaySegmentControlUIState>
        get() = _changeBackgroundColorForSelectedSegmentControl

    private var selectedSegmentedControlClickState: ThreeWaySegmentControlUIState? =null
    private val _colorValueFromWheel: MutableLiveData<Int> = MutableLiveData()

    val segmentedControlColorUIState: SegmentedControlColorUIState = SegmentedControlColorUIState(
        tealControl = Constants.convertHexCodeToColorCode(Constants.TEAL_COLOR_CODE),
        greenControl = Constants.convertHexCodeToColorCode(Constants.GREEN_COLOR_CODE),
        orangeControl = Constants.convertHexCodeToColorCode(Constants.ORANGE_COLOR_CODE)
    )

    val colorValueFromWheel: LiveData<ThreeWaySegmentControlUIState> =
        Transformations.map(_colorValueFromWheel){ colorCode ->
             selectedSegmentedControlClickState?.let {
                 it.colorCode = colorCode
                 selectedSegmentedControlClickState
             }
         }

    fun getColorValueFromWheel(colorCode: Int) {
        _colorValueFromWheel.value = colorCode
    }


    fun changeSegmentControllerClickState(input: ThreeWaySegmentControlUIState) {
        _changeBackgroundColorForSelectedSegmentControl.value = input
        selectedSegmentedControlClickState = input
    }


}