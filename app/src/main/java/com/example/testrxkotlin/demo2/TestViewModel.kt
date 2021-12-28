package com.example.testrxkotlin.demo2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * 对应test的ViewModel
 */
class TestViewModel : ViewModel() {

    //专门对应json数据中的Address数据List
    val getDataList = MutableLiveData<List<Address>>()

}