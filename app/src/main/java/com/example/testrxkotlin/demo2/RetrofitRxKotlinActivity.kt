package com.example.testrxkotlin.demo2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.testrxkotlin.databinding.ActivityRetrofitRxKotlinBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitRxKotlinActivity : AppCompatActivity() {
    val TAG = "RetrofitRxKotlinActivity"
    //返回到主目录上
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return true
    }
    private lateinit var binding: ActivityRetrofitRxKotlinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetrofitRxKotlinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        test1()

        binding.Button2.setOnClickListener { test2() }
    }

    //简单创建。但是得到结果后，不能在前端实现
    fun test1(){
        val retrofit = Retrofit.Builder()
            .baseUrl("http://zipcloud.ibsnet.co.jp/")
            .addConverterFactory(GsonConverterFactory.create()) //把json转为gson，才可以直接用LiveData.postValue
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

        val service = retrofit.create(ApiClient::class.java)
            service.getZipCode("0790177").subscribe ({
                Log.e(TAG, "result: $it")
            }, { error ->
                error.message?.let { Log.e(TAG, it) }
            })
    }

    //得到结果后，在前端实现
    fun test2(){
        val retrofit = Retrofit.Builder()
            .baseUrl("http://zipcloud.ibsnet.co.jp/")
            .addConverterFactory(GsonConverterFactory.create()) //把json转为gson，才可以直接用LiveData.postValue
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

        val service = retrofit.create(ApiClient::class.java)

        service.getZipCode("0790177")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                Log.d(TAG, "response=$it")
                binding.result.text = it.toString()
            }
            .doOnError {
            }
            .subscribe()
    }


    val observer: Observer<Any> = object : Observer<Any> {
        override fun onComplete() {
            Log.e(TAG, ("All Completed"))

        }

        override fun onNext(item: Any) {
            Log.e(TAG, ("Next $item"))


            binding.result.text=item.toString()
        }

        override fun onError(e: Throwable) {
            Log.e(TAG, ("Error Occured ${e.message}"))
        }

        override fun onSubscribe(d: Disposable) {
            Log.e(TAG, ("New Subscription "))
        }
    }
}