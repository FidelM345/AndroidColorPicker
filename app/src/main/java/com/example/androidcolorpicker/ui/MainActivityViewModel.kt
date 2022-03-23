package com.example.androidcolorpicker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.androidcolorpicker.util.Constants

class MainActivityViewModel : ViewModel() {
    private val _threeWaySegmentControlUIState: MutableLiveData<ThreeWaySegmentControlUIState> =
        MutableLiveData()
    val changeBackgroundColorForSelectedSegmentControl: LiveData<ThreeWaySegmentControlUIState>
        get() = _threeWaySegmentControlUIState

    private var selectedSegmentedControlClickState: ThreeWaySegmentControlUIState? =null
    private val _colorValueFromWheel: MutableLiveData<Int> = MutableLiveData()

    val segmentedControlColorUIState: SegmentedControlColorUIStater = SegmentedControlColorUIStater(
        tealControl = Constants.convertColorCodeToInt(Constants.TEAL_COLOR_CODE),
        greenControl = Constants.convertColorCodeToInt(Constants.GREEN_COLOR_CODE),
        orangeControl = Constants.convertColorCodeToInt(Constants.ORANGE_COLOR_CODE)
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
        _threeWaySegmentControlUIState.value = input
        selectedSegmentedControlClickState = input
    }


}