package com.revenco.viewpager2_example.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revenco.viewpager2_example.Event

/**
 *  Copyright © 2021/4/4 Hugecore Information Technology (Guangzhou) Co.,Ltd. All rights reserved.
 *  author: chenqi
 */

class BlankViewModel : ViewModel() {

    private val _isNumberCount = MutableLiveData<Event<Int>>()

    val isNumberCount
        get() = _isNumberCount

    init {
        _isNumberCount.value = Event(100)
    }
}