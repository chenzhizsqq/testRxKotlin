package com.example.testrxkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.toObservable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //test1()
        //test1_2()
        //test2()
        //test2_2()
        //test3()
        //test3_2()
        //test3_3()
        //test3_4()
        //test3_5()
        //test3_6()
        //test3_7()
        //test3_8()
        test3_9()
        //test3_10()
    }

    //https://www.jianshu.com/p/f6e7d2775bad
    private fun test1() {
        val list: List<Any> =
            listOf("One", 2, "Three", "Four", 4.5, "Five", 6.0f)  // 类型标注可省,这里添加是为了看得清楚,下同
        val observable: Observable<Any> = list.toObservable()  // Observable 后续会提及

        observable.subscribeBy(  // 1. 下面用到了 Kotlin 的命名参数  2. subscribe 后续会提及
            onNext = { Log.e(TAG, "onCreate onNext: $it") },
            onError = { Log.e(TAG, "onCreate onError: $it") },
            onComplete = { Log.e(TAG, "Done: !") },
        )
    }
    /*
    onCreate onNext: One
    onCreate onNext: 2
    onCreate onNext: Three
    onCreate onNext: Four
    onCreate onNext: 4.5
    onCreate onNext: Five
    onCreate onNext: 6.0
    Done: !
     */

    //https://www.jianshu.com/p/f6e7d2775bad
    fun isEvenOrOdd(n: Int): String = if ((n % 2) == 0) "Even" else "Odd"  // 如果数字为偶数返回 "Even" 否则返回 "Odd"
    fun test1_2() {  // Subject 后续会提及
        val subject: Subject<Int> = PublishSubject.create()

        subject.map { isEvenOrOdd(it) }  // map 后续会提及
            .subscribe { Log.e(TAG,("The number is $it")) }

        subject.onNext(4)
        subject.onNext(9)
    }
    /*
    The number is Even
    The number is Odd
     */

    //https://www.jianshu.com/p/a6b8c545505f
    fun test2() {
        val maybeValue: Maybe<Int> = Maybe.just(14)
        maybeValue.subscribeBy(
            onComplete = { Log.e(TAG,("没有值的时候调用的函数")) },
            onError = { Log.e(TAG,("出错处理函数 $it")) },
            onSuccess = { Log.e(TAG,("有值的时候调用的函数,Completed with value $it")) }
        )
    }
    /*
    Completed with value 14
     */

/*  Observable	可被观察的对象	电台
    Observer	观察者	        收音机
    subscribe	订阅	            调节收音机至电台频率
    */
    //https://www.jianshu.com/p/a6b8c545505f
    val observer: Observer<Any> = object : Observer<Any> {
        override fun onComplete() {
            Log.e(TAG,("All Completed"))
        }

        override fun onNext(item: Any) {
            Log.e(TAG,("Next $item"))
        }

        override fun onError(e: Throwable) {
            Log.e(TAG,("Error Occured ${e.message}"))
        }

        override fun onSubscribe(d: Disposable) {
            Log.e(TAG,("New Subscription "))
        }
    }
    fun test2_2() {
        val observable: Observable<Any> =
            listOf("One", 2, "Three", "Four", 4.5, "Five", 6.0f).toObservable()
        // toObservable 见下一节
        observable.subscribe(observer)
    }
    /*
    New Subscription
    Next One
    Next 2
    Next Three
    Next Four
    Next 4.5
    Next Five
    Next 6.0
    All Completed
     */


    //https://www.jianshu.com/p/6247968a9257
    fun test3() {
        val observable: Observable<Int> = Observable.create<Int> {
            // it: ObservableEmitter<String!>
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
            it.onComplete()
        }

        observable.subscribe(observer) // observer 同上一节
    }
    /*
    New Subscription
    Next 1
    Next 2
    Next 3
    All Completed
     */

    //https://www.jianshu.com/p/6247968a9257
    fun test3_2() {
        val observable: Observable<Int> = Observable.create<Int> {
            // it: ObservableEmitter<String!>
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
            it.onError(Exception("My Custom Exception"))
        }

        observable.subscribe(observer) // observer 同上一节
    }
    /*
    New Subscription
    Next 1
    Next 2
    Next 3
    Error Occured My Custom Exception
     */


    fun test3_3() {
        val observable: Observable<Int> = Observable.create<Int> {
            it.onNext(1) // 其实它也可省,可以删除看看效果
        }

        observable.subscribe(observer)
    }
    /*
    New Subscription
    Next 1
     */


    fun test3_4() {
        val observable: Observable<Int> = Observable.create<Int> {
            it.onComplete()
            it.onNext(1)    //只要it.onComplete()出现后，it.onNext(1) 就不起作用了
        }

        observable.subscribe(observer)
    }
    /*
    New Subscription
    All Completed
     */

    fun test3_5() {
        val list = listOf(1, 2, 3, 4)
        val observable: Observable<Int> = Observable.fromIterable(list)
        // 我们也可以用 list.toObservable() 替代 Observable.fromIterable(list)
        observable.subscribe(observer)
    }
    /*
    New Subscription
    Next 1
    Next 2
    Next 3
    Next 4
    All Completed
     */

    fun test3_6() {
        Observable.just(54).subscribe(observer)
        Observable.just(listOf(1, 2, 3)).subscribe(observer)
        Observable.just(1, 2, 3).subscribe(observer)
    }
    /*
    New Subscription
    Next 54
    All Completed
    New Subscription
    Next [1, 2, 3]
    All Completed
    New Subscription
    Next 1
    Next 2
    Next 3
    All Completed
     */

    fun test3_7() {
        // 下面两者等效
        Observable.just(1, 2, 3).subscribe(observer)
        Observable.fromIterable(listOf(1, 2, 3)).subscribe(observer)
    }
    /*
    New Subscription
    Next 1
    Next 2
    Next 3
    All Completed
    New Subscription
    Next 1
    Next 2
    Next 3
    All Completed
     */

    /*  test3_7 与 test3_3 的对比
    just fromX 与 create 的区别
    create 中需要显式弹射结束标志(it.onComplete()),Observer 才会调用 onComplete 方法 (3.3.kt 中,没有 it.onComplete() 输出中就没有 All Completed)
    just fromX 会自动弹射结束标志 (3.6.kt 3.7.kt 3.8.kt 中均有输出 All Completed)
     */

    //empty 没有值
    fun test3_8() {
        Observable.empty<String>().subscribe(observer)
    }
    /*
    New Subscription
    All Completed
     */

    //interval 隔一定的时间弹射一个值
    fun test3_9() {
        Observable.interval(600, TimeUnit.MILLISECONDS).subscribe(observer)
        Thread.sleep(2000) // 要有这一行
    }

    //timer 一段时间后弹射一个值
    fun test3_10() {
        Observable.timer(20, TimeUnit.MILLISECONDS).subscribe(observer)
        Thread.sleep(50) // 要有这一行
    }
    /*
    New Subscription
    Next 0
    All Completed
     */
}