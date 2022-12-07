package com.maverick.coroutinecheezycode

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _counter = MutableStateFlow(0)
    val counter: StateFlow<Int> = _counter

    fun increaseState() {
        _counter.value++
    }

    fun decreaseState() {
        _counter.value--
    }

}