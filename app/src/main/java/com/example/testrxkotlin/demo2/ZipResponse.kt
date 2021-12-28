package com.example.testrxkotlin.demo2

data class ZipResponse(
    var message: String? = null,
    var status: Int? = null,
    var results: ArrayList<Address> = ArrayList()
)
