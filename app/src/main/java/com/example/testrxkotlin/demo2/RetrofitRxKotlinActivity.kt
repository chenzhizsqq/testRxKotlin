package com.example.testrxkotlin.demo2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testrxkotlin.databinding.ActivityRetrofitRxKotlinBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit




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


        binding.Button1.setOnClickListener { test1() }
        binding.Button2.setOnClickListener { test2() }
        binding.Button3.setOnClickListener { test3() }
        binding.Button4.setOnClickListener { test4() }
        binding.Button5.setOnClickListener { test5() }

        binding.Button6.setOnClickListener { test6() }
        //viewModel观察
        viewModel.getDataList.observe(this, {
            binding.result.text = it.toString()
        })

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


    //得到结果后，在前端实现 扩展Observer
    fun test3(){
        val retrofit = Retrofit.Builder()
            //.client(build.build())
            .baseUrl("http://zipcloud.ibsnet.co.jp/")
            .addConverterFactory(GsonConverterFactory.create()) //把json转为gson，才可以直接用LiveData.postValue
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

        val service = retrofit.create(ApiClient::class.java)
        service.getZipCode("0790177")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
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


    fun test4(){
        //连接器设置的创建
        val build = OkHttpClient.Builder()
            .connectTimeout(60,TimeUnit.SECONDS)    //连接超时  SECONDS 秒
            .writeTimeout(60,TimeUnit.SECONDS)      //写超时   SECONDS 秒
            .readTimeout(60,TimeUnit.SECONDS)       //读取超时  SECONDS 秒

        val retrofit = Retrofit.Builder()
            .client(build.build())                              //连接器设置的配置
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


    fun test5(){
        val build = OkHttpClient.Builder()
            .connectTimeout(60,TimeUnit.SECONDS)    //连接超时  SECONDS 秒
            .writeTimeout(60,TimeUnit.SECONDS)      //写超时   SECONDS 秒
            .readTimeout(60,TimeUnit.SECONDS)       //读取超时  SECONDS 秒

        //接收log管理器
        val logging = HttpLoggingInterceptor {
            Log.e("OkHttp logging :", it)
        }
        logging.level = HttpLoggingInterceptor.Level.BODY

        //把log管理器，加入到OkHttpClient.Builder()中
        build.addInterceptor(logging)

        val retrofit = Retrofit.Builder()
            .client(build.build())                              //连接器设置的配置
            .baseUrl("http://zipcloud.ibsnet.co.jp/")
            .addConverterFactory(GsonConverterFactory.create()) //把json转为gson，才可以直接用LiveData.postValue
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

        val service = retrofit.create(ApiClient::class.java)
        service.getZipCode("0790177")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                Toast.makeText(this, "能够在logcat中，看到服务器连接的log", Toast.LENGTH_SHORT).show()
                binding.result.text = it.toString()
            }
            .doOnError {
            }
            .subscribe()
    }


    private val viewModel = TestViewModel()
    fun test6(){
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
                //把数据导入到viewModel中
                viewModel.getDataList.postValue(it.results)
            }
            .doOnError {
            }
            .subscribe()
    }
}