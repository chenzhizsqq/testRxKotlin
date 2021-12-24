package com.example.testrxkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.toObservable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject


class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //test1()
        //test1_2()
        test2()
    }

    //https://www.jianshu.com/p/f6e7d2775bad
    private fun test1() {
        val list: List<Any> =
            listOf("One", 2, "Three", "Four", 4.5, "Five", 6.0f)  // 类型标注可省,这里添加是为了看得清楚,下同
        val observable: Observable<Any> = list.toObservable()  // Observable 后续会提及

        observable.subscribeBy(  // 1. 下面用到了 Kotlin 的命名参数  2. subscribe 后续会提及
            onNext = { Log.e(TAG, "onCreate onNext: $it") },
            onError = { Log.e(TAG, "onCreate onError: $it") },
            onComplete = { Log.e(TAG, "Done: !") }
        )
    }

    fun isEvenOrOdd(n: Int): String = if ((n % 2) == 0) "Even" else "Odd"  // 如果数字为偶数返回 "Even" 否则返回 "Odd"

    //https://www.jianshu.com/p/f6e7d2775bad
    fun test1_2() {  // Subject 后续会提及
        val subject: Subject<Int> = PublishSubject.create()

        subject.map { isEvenOrOdd(it) }  // map 后续会提及
            .subscribe { Log.e(TAG,("The number is $it")) }

        subject.onNext(4)
        subject.onNext(9)
    }

    //https://www.jianshu.com/p/a6b8c545505f
    fun test2() {
        val maybeValue: Maybe<Int> = Maybe.just(14)
        maybeValue.subscribeBy(
            onComplete = { Log.e(TAG,("Completed Empty")) },
            onError = { Log.e(TAG,("Error $it")) },
            onSuccess = { Log.e(TAG,("Completed with value $it")) }
        )
    }
}