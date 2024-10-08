package com.example.testrxkotlin.demo2

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

//http://zipcloud.ibsnet.co.jp/api/search?zipcode=0790177
interface ApiClient {

    //获取http://zipcloud.ibsnet.co.jp/api/search 中的 zipcode
    @GET("api/search")
    fun getZipCode(@Query("zipcode") zipcode: String): Observable<ZipResponse>
}