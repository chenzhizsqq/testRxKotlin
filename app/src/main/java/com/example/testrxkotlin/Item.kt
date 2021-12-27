package com.example.testrxkotlin

import android.util.Log

//第七章节，都是用这个data来测试
data class Item(val id:Int){
    init{
        Log.e("Item", " create $id" )
    }
}