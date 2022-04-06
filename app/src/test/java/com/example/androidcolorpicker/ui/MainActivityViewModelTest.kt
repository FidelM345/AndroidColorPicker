package com.example.androidcolorpicker.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import com.example.androidcolorpicker.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainActivityViewModelTest{
    private lateinit var mainActivityViewModel: MainActivityViewModel
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel(){
        //given
        mainActivityViewModel = MainActivityViewModel()
    }

    @Test
    fun getColorValueFromWheel_emitsColorCodeValuesFromColorWheel() {
        //when
        val colorCodeFromColorWheel = 24356
        mainActivityViewModel.getColorValueFromWheel(colorCodeFromColorWheel)
        mainActivityViewModel.changeSegmentControllerClickState(ThreeWaySegmentControlUIState(true))
        //then
        val result = mainActivityViewModel.colorValueFromWheel.getOrAwaitValue()
        assertEquals(colorCodeFromColorWheel, result.colorCode)

    }

    @Test
    fun changeSegmentControllerClickState_greenButtonClicked_returnsTrueOthersFalse(){
        //when
        mainActivityViewModel.changeSegmentControllerClickState(ThreeWaySegmentControlUIState(green = true))
       //then
        val results = mainActivityViewModel.changeBackgroundColorForSelectedSegmentControl.getOrAwaitValue()
        assertTrue(results.green)
        assertFalse(results.orange)
        assertFalse(results.teal)
    }

    @Test
    fun changeSegmentControllerClickState_tealButtonClicked_returnsTrueOthersFalse(){
        //when
        mainActivityViewModel.changeSegmentControllerClickState(ThreeWaySegmentControlUIState(teal = true))
        //then
        val results = mainActivityViewModel.changeBackgroundColorForSelectedSegmentControl.getOrAwaitValue()
        assertTrue(results.teal)
        assertFalse(results.green)
        assertFalse(results.orange)
    }

    @Test
    fun changeSegmentControllerClickState_orangeButtonClicked_returnsTrueOthersFalse(){
        //when
        mainActivityViewModel.changeSegmentControllerClickState(ThreeWaySegmentControlUIState(orange = true))
        //then
        val results = mainActivityViewModel.changeBackgroundColorForSelectedSegmentControl.getOrAwaitValue()
        assertTrue(results.orange)
        assertFalse(results.green)
        assertFalse(results.teal)
    }
}