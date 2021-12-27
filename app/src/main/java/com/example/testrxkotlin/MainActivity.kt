package com.example.testrxkotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.toObservable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.*
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
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
        //test3_9()
        //test3_10()
        //test3_11()
        //test4_1()
        //test4_2()
        //test4_3()
        //test4_4()
        //test4_5()
        //test5_1()
        //test5_2()
        //test5_3()
        //test5_4()
        //test5_5()
        //test5_6()
        //test5_7()
        //test6_1()
        //test6_2()
        //test6_3()
        //test6_4()
        //test6_5()
        //test6_6()
        //test6_7()
        //test6_8()
        //test6_9()
        //test7_1()
        //test7_2()
        //test7_3()
        //test7_4()
        //test7_5()
        //test7_6()
        //test7_7()
        //test8_1()
        //test8_2()
        //test8_3()
        //test8_4()
        test8_5()
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

    //Observable.empty 没有值类型的操作
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
        Thread.sleep(2000) // 要有这一行，要不然会影响下个线程的操作
    }
    /*
    New Subscription
    Next 0
    Next 1
    Next 2
    Next 3
    ...
     */

    //timer 一段时间后弹射一个值
    fun test3_10() {
        Observable.timer(20, TimeUnit.MILLISECONDS).subscribe(observer)
        Thread.sleep(50) // 要有这一行，要不然会影响下个线程的操作
    }
    /*
    New Subscription
    Next 0
    All Completed
     */

    //range 一个范围内依次弹射
    fun test3_11() {
        Observable.range(4, 3).subscribe(observer)
    }
    /*
    New Subscription
    Next 4
    Next 5
    Next 6
    All Completed
     */

    //https://www.jianshu.com/p/efc8fb38883c
    //第四节 Observer Subscribe 与 Hot/Cold Observable
    fun test4_1() {
        val observable: Observable<Int> = Observable.range(1, 3)
        observable.subscribe({  // 我知道你要问我为什么 subscribe 后面还可以接三个 Lambda,先看例子,下面说
            //onNext method
            Log.e(TAG, "Next $it")
        }, {
            //onError Method
            Log.e(TAG, "Error ${it.message}")
        }, {
            //onComplete Method
            Log.e(TAG, "All Completed")
        })
        //用这样的创建，创建时就没有onSubscribe函数的调用
    }
    /*
    Next 1
    Next 2
    Next 3
    All Completed
     */

    //Disposable 就是停止订阅，马上中止线程。其实就是相当于马上废弃当前线程。
    fun test4_2() {

        val observale: Observable<Long> = Observable.interval(100, TimeUnit.MILLISECONDS)

        val observer: Observer<Long> = object : Observer<Long> {
            lateinit var disposable: Disposable

            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onNext(item: Long) {
                if (item >= 5 && !disposable.isDisposed) {
                    disposable.dispose()
                    Log.e(TAG, "Disposed")
                }
                Log.e(TAG, "Received $item")
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "Error ${e.message}")
            }

            override fun onComplete() {
                Log.e(TAG, "Complete")
            }

        }

        observale.subscribe(observer)
        Thread.sleep(1000)
    }
    /*
    Received 0
    Received 1
    Received 2
    Received 3
    Received 4
    Disposed        //dispose 处理后不会执行 observer 的 onComplete 方法(所以 Complete 没有输出)
    Received 5      //执行废弃后，还会继续做完onNext(item: Long)那个函数，之后就全部中止废弃。
     */

    //Hot/Cold Observable
    // Cold Observables 就是比较死板的，只要订阅了，全部的流程都会来一遍。相当于单线程。
    fun test4_3() {
        val observable: Observable<Int> = listOf(1, 2, 3, 4).toObservable()
        observable.subscribe(observer)
        observable.subscribe(observer)
    }
    /*
    New Subscription
    Next 1
    Next 2
    Next 3
    Next 4
    All Completed
    New Subscription
    Next 1
    Next 2
    Next 3
    Next 4
    All Completed
     */


    //Hot Observable    这个就不会死板，一旦线程有空了，就会马上给你放。相当于多线程
    fun test4_4() {
        val connectableObservable = listOf(1, 2, 3,4).toObservable().publish()  // 注释1
        connectableObservable.subscribe { Log.e(TAG, "Subscription 1: $it") }  // 描点1
        connectableObservable.subscribe { Log.e(TAG, "Subscription 2: $it") }  // 描点2
        connectableObservable.connect() // 这里就是之前已经publish()创建和subscribe()配置后，就开始发送
        connectableObservable.subscribe { Log.e(TAG, "Subscription 3: $it") }  // 注释3
    }
    /*
    Subscription 1: 1
    Subscription 2: 1
    Subscription 1: 2
    Subscription 2: 2
    Subscription 1: 3
    Subscription 2: 3
    Subscription 1: 4
    Subscription 2: 4
     */

    //Hot Observable中，因为用上了Thread.sleep(20)，所以Subscription 3的起步就慢。
    // Subscription 3的显示是Subscription 3: 2，从2开始
    fun test4_5() {
        val connectableObservable = Observable.interval(10, TimeUnit.MILLISECONDS).publish()
        connectableObservable.subscribe { Log.e(TAG, "Subscription 1: $it") }
        connectableObservable.subscribe { Log.e(TAG, "Subscription 2: $it") }
        connectableObservable.connect()  // ConnectableObservable 开始发送消息
        Log.e(TAG, "Sleep 1 starts")
        Thread.sleep(20)
        Log.e(TAG, "Sleep 1 ends")
        connectableObservable.subscribe { Log.e(TAG, "Subscription 3: $it") }  // 不用再次调用 connect 方法
        Log.e(TAG, "Sleep 2 starts")
        Thread.sleep(30)
        Log.e(TAG, "Sleep 2 ends")
    }
    /*
    Sleep 1 starts
    Subscription 1: 0
    Subscription 2: 0
    Subscription 1: 1
    Subscription 2: 1
    Sleep 1 ends
    Sleep 2 starts
    Subscription 1: 2
    Subscription 2: 2
    Subscription 3: 2
    Subscription 1: 3
    Subscription 2: 3
    Subscription 3: 3
    Subscription 1: 4
    Subscription 2: 4
    Subscription 3: 4
    Sleep 2 ends
    ...
     */

    //第五章来了     Hot Observable 的另一种实现 ---- Subject
    //https://www.jianshu.com/p/0dd221428626
    fun test5_1() {
        val observable = Observable.interval(10, TimeUnit.MILLISECONDS)
        val subject = PublishSubject.create<Long>()  // 注释1

        observable.subscribe(subject)  // 描点1 Subject 充当 Observer 角色
        subject.subscribe { Log.e(TAG, "Received $it") }  // 描点2 Subject 充当 Observable 角色
        Thread.sleep(60)
    }
    /*
    Received 0
    Received 1
    Received 2
    Received 3
    Received 4
    ...
     */


    //Subject 是 Hot Observable 的一种      是对应多线程的顺序，从零开始
    fun test5_2() {
        val observable = Observable.interval(100, TimeUnit.MILLISECONDS)

        observable.subscribe { Log.e(TAG, "Subscription A Received $it") }
        Thread.sleep(200)
        observable.subscribe { Log.e(TAG, "Subscription B Received $it") }
        Thread.sleep(300)
    }
    /*
    Subscription A Received 0
    Subscription A Received 1
    Subscription A Received 2
    Subscription B Received 0       //从零开始的
    Subscription A Received 3
    Subscription B Received 1
    Subscription A Received 4
    Subscription B Received 2
    Subscription A Received 5
    ...
     */

    //PublishSubject 是 Subject 的一种      不是对应顺序，从最后一个线程开始
    //用作对比test5_2   PublishSubject
    fun test5_3() {
        val observable = Observable.interval(100, TimeUnit.MILLISECONDS)
        val subject = PublishSubject.create<Long>()

        observable.subscribe(subject)

        subject.subscribe { Log.e(TAG, "Subscription A Received $it") }
        Thread.sleep(300)
        subject.subscribe { Log.e(TAG, "Subscription B Received $it") }
        Thread.sleep(200)
    }
    /*
    Subscription A Received 0
    Subscription A Received 1
    Subscription A Received 2
    Subscription A Received 3
    Subscription B Received 3        //有对比了，不是从0开始的
    Subscription A Received 4
    Subscription B Received 4
    Subscription A Received 5
    ...
     */

    //接收所有值,并把最后一个值从 Observable 接口处弹出去
    fun test5_4() {
        val observable = Observable.just(1, 2, 3, 4)
        val subject = AsyncSubject.create<Int>()
        observable.subscribe(subject)
        subject.subscribe(observer)
    }
    /*
    New Subscription
    Next 4              //就是弹出最后一个值
    All Completed
     */

    //AsyncSubject就算是多次订阅，也是直接跳到最后一个
    fun test5_5() {
        val subject = AsyncSubject.create<Int>()
        subject.onNext(1)
        subject.onNext(2)
        subject.subscribe(observer)  // 订阅1
        subject.onNext(3)
        subject.subscribe(observer)  // 订阅2
        subject.onNext(4)
        subject.onComplete()
    }
    /*
    New Subscription
    New Subscription
    Next 4  // 订阅1(我知道你要问为什么不输出 2 而是 4,下面有解释)
    All Completed
    Next 4  // 订阅2
    All Completed
     */


    //BehaviorSubject 开始时，是跳到到订阅前的那个一个
    fun test5_6() {
        val subject = BehaviorSubject.create<Int>()
        subject.onNext(1)
        subject.onNext(2)
        subject.subscribe(observer) // 订阅1
        subject.onNext(3)
        subject.subscribe(observer) // 订阅2
        subject.onNext(4)
        subject.onComplete()
    }
    /*
    New Subscription
    Next 2  // 订阅1 获取到了 `2`  而跳过了 `1`
    Next 3  // 订阅1 获取到了订阅之后的值
    New Subscription
    Next 3  // 订阅2
    Next 4  // 订阅1
    Next 4  // 订阅2
    All Completed
    All Completed
    */

    //ReplaySubject  它和 Cold Observable 的性质差不多
    fun test5_7() {
        val subject = ReplaySubject.create<Int>()
        subject.onNext(1)
        subject.onNext(2)
        subject.subscribe(observer)
        subject.onNext(3)
        subject.subscribe(observer)
        subject.onComplete()
    }

    //https://www.jianshu.com/p/06bf9dff0d75
    //Operator 与 Marble Diagram
    /*中文翻译
    Operator 运算符
    Marble Diagram 弹珠图*/


    //Transforming	把 源 Observable 中的值进行变换后弹出	Map
    // Map 是一种函数, 不是数据结构
    fun test6_1() {
        val observable = Observable.just(1, 2, 3)
        observable.map { x -> 10 * x }.subscribe(observer)  // 这里完全可以用 10*it ,为了和下面的图片一致我没有这么做
    }
    /*
    New Subscription
    Next 10
    Next 20
    Next 30
    All Completed
     */


    //Filter(Filtering)     把 源 Observable 中的值选择性地弹出
    //下面就有条件添加
    fun test6_2() {
        val observable = Observable.just(2, 30, 22, 5, 60, 1)
        observable.filter { x -> x > 10 }.subscribe(observer)  // it > 10
    }
    /*
    New Subscription
    Next 30
    Next 22
    Next 60
    All Completed
     */

    //FlatMap(Transforming) 和 Kotlin List 的 flatMap 相似
    fun test6_3(){
        val observable = Observable.just(1, 5, 9)  // 数字没有特殊含义
        observable
            .flatMap { x -> Observable.just(x + 1, x + 2) }  // 这个例子非常牵强
            .subscribe(observer)
    }
    /*
    New Subscription
    Next 2
    Next 3
    Next 6
    Next 7
    Next 10
    Next 11
    All Completed
     */

    //DefaultIfEmpty(Conditional and Boolean)
    //当 Observable 中没有值的时候，我们订阅什么也得不到。比如下面的例子
    fun test6_4() {
        Observable.range(0, 10)
            .filter { it > 15 }
            .subscribe(observer)
    }
    /*
    New Subscription
    All Completed
     */

    //那如果我们想在 Observable 没有值的时候给出一个默认值呢, 见下例
    fun test6_5() {
        Observable.range(0, 10)
            .filter { it > 15 }
            .defaultIfEmpty(15)
            .subscribe(observer)
    }
    /*
    New Subscription
    Next 15             //因为添加了.defaultIfEmpty(15)
    All Completed
     */

    //开始时候是什么元素
    fun test6_6() {
        Observable.just(2, 3)
            .startWithItem(1)       //开始时候是什么元素
            .subscribe(observer)
    }
    /*
    New Subscription
    Next 1
    Next 2
    Next 3
    All Completed
     */

    //数当前元素有多少各 sum
    fun test6_7() {
        // Single 会在之后介绍
        val count: Single<Long> = Observable.just(2, 30, 22, 5, 60, 1).count()
        // subscribeBy 会在之后介绍
        count.subscribeBy { Log.e(TAG, it.toString()) }
    }
    //6

    //Scan(和 Map 一样, 是 Transforming)
    fun test6_8() {
        Observable.just(1, 2, 3, 4, 5)
            .scan { x, y -> x + y }
            .subscribe(observer)
    }
    /*
    New Subscription
    Next 1
    Next 3
    Next 6
    Next 10
    Next 15
    All Completed
     */

    //再来看一个 Scan 的例子 (这个例子是为了更进一步了解 Scan, 不是为了演示 Marble Diagram)
    fun test6_9(){
        Observable.just("1","2","3","4","5")
            .scan { x, y -> x + " " + y  }
            .subscribe(observer)
    }
    /*
    New Subscription
    Next 1
    Next 1 2
    Next 1 2 3
    Next 1 2 3 4
    Next 1 2 3 4 5
    All Completed
     */


    /*
    一个值被 Observable 弹出 -> 被 Observer 处理 -> 下一个值被弹出 -> ...
    这是因为 Observable 和 Observer 运行在一个线程中,
    所以在 Observer 没处理完上一个值之前 Observable 是不能弹出下一个值的。
     */
    fun test7_1(){
        Observable.just(1,2,3).map { Item(it) }
            .subscribe {
                Log.e(TAG, "Received $it")
                Thread.sleep(100)
            }
        Thread.sleep(1000)
    }
    /*
    Item:  create 1
    MainActivity: Received Item(id=1)
    Item:  create 2
    MainActivity: Received Item(id=2)
    Item:  create 3
    MainActivity: Received Item(id=3)
     */


    //这里只需要知道这一行代码使得 Observer 在另一个线程中运行即可
    fun test7_2() {
        Observable.just(1, 2, 3).map { Item(it) }
            .observeOn(Schedulers.newThread())
            .subscribe {
                Thread.sleep(100)
                Log.e(TAG, "Received $it")
            }

        Thread.sleep(1000)
    }
    /*
    Item:  create 1
    Item:  create 2
    Item:  create 3
    MainActivity: Received Item(id=1)
    MainActivity: Received Item(id=2)
    MainActivity: Received Item(id=3)
     */

    //这里是 Subscriber 而不是 Observer, 但是由于用的 Lambda 形式, 看起来一样。
    fun test7_3() {
        Flowable.just(1,2,3).map { Item(it) }
            .observeOn(Schedulers.newThread())
            .subscribe{
                Thread.sleep(100)
                Log.e(TAG, "Received $it")
            }
        Thread.sleep(1000)
    }
    /*
    Item:  create 1
    Item:  create 2
    Item:  create 3
    MainActivity: Received Item(id=1)
    MainActivity: Received Item(id=2)
    MainActivity: Received Item(id=3)
     */
    //暂时结果是一致，但是多的数据后，就不一样了。



    //Flowable 不会一下子把所有值全部弹出, 它会一块一块的弹, 当 Subscriber 跟上时才会继续
    //Flowable 会维护一个默认大小为 128 个元素的缓冲区, 被弹出的元素会暂存其中。如果满了 Flowable 就会暂时停止弹射。
    fun test7_4() {
        Flowable.range(1,260).map { Item(it) }
            .observeOn(Schedulers.newThread())
            .subscribe{
                Thread.sleep(100)
                Log.e(TAG, "Received $it")
            }
        Thread.sleep(2700)
    }
    /*
    ...
    Item:  create 126
    Item:  create 127
    Item:  create 128                   // 当 Flowable 弹出 128 个值就暂时停止了, 缓冲区达到上限(128)
    MainActivity: Received Item(id=1)
    MainActivity: Received Item(id=2)
    MainActivity: Received Item(id=3)
    MainActivity: Received Item(id=4)
    MainActivity: Received Item(id=5)
    ...
    MainActivity: Received Item(id=95)
    MainActivity: Received Item(id=96)  // Subscriber 仅仅处理了 96 个值, 缓冲区没有被清空
    Item:  create 129
    Item:  create 130
    Item:  create 131
    ...
     */


    //test7_5()中，调用
    val subscriber_1 = object : Subscriber<Item> {
        override fun onSubscribe(subscription: Subscription) {
            subscription.request(4)  // 注释1  限定请求 4 个值。 如果删掉这一行, 我们就没有限定请求数量, 一个值都接收不到
            Log.e(TAG,"New Subscription ")
        }

        override fun onNext(s: Item) {
            Thread.sleep(200)
            Log.e(TAG,"Subscriber received " + s)
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
        }

        override fun onComplete() {
            Log.e(TAG,"Done!")
        }
    }

    fun test7_5() {
        Flowable.range(1, 6)
            .map { Item(it) }
            .observeOn(Schedulers.newThread())
            .subscribe(subscriber_1)
        Thread.sleep(2000)
    }
    /*
    MainActivity: New Subscription
    Item:  create 1
    Item:  create 2
    Item:  create 3
    Item:  create 4
    Item:  create 5
    Item:  create 6
    MainActivity: Subscriber received Item(id=1)
    MainActivity: Subscriber received Item(id=2)
    MainActivity: Subscriber received Item(id=3)
    MainActivity: Subscriber received Item(id=4)    //我们只请求 4 个值, 数据流并没有到结尾, 系统没有调用 onComplete 方法。
     */


    fun test7_6() {
        Flowable.range(1, 4)            //改为4试一下
            .map { Item(it) }
            .observeOn(Schedulers.newThread())
            .subscribe(subscriber_1)                //继续调用subscriber_1
        Thread.sleep(2000)
    }
    /*
    MainActivity: New Subscription
    Item:  create 1
    Item:  create 2
    Item:  create 3
    Item:  create 4
    MainActivity: Subscriber received Item(id=1)
    MainActivity: Subscriber received Item(id=2)
    MainActivity: Subscriber received Item(id=3)
    MainActivity: Subscriber received Item(id=4)
    MainActivity: Done!                             //因为调用4个，所以这里可以Done了
     */


    val subscriber_2 = object : Subscriber<Item> {
        lateinit var subscription: Subscription  // 与 subscriber_1 相比多了这一行
        override fun onSubscribe(subscription: Subscription) {
            this.subscription = subscription  // 与 subscriber_1 相比多了这一行
            subscription.request(4)
            Log.e(TAG,"New Subscription ")
        }

        override fun onNext(s: Item) {
            Thread.sleep(200)
            Log.e(TAG,"Subscriber received " + s)
            if (s.id == 4) {                    // |\
                Log.e(TAG,"Requesting two more")  // | \
                subscription.request(2)         // | /--- 与 subscriber_1 相比多了这几行
            }                                   // |/
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
        }

        override fun onComplete() {
            Log.e(TAG,"Done!")
        }
    }

    fun test7_7() {
        Flowable.range(1, 6)
            .map { Item(it) }
            .observeOn(Schedulers.newThread())
            .subscribe(subscriber_2) // 只有这一行与 7.5.kt 相比有变动
        Thread.sleep(2000)
    }
    /*
    MainActivity: New Subscription
    Item:  create 1
    Item:  create 2
    Item:  create 3
    Item:  create 4
    Item:  create 5
    Item:  create 6
    MainActivity: Subscriber received Item(id=1)
    MainActivity: Subscriber received Item(id=2)
    MainActivity: Subscriber received Item(id=3)
    MainActivity: Subscriber received Item(id=4)
    MainActivity: Requesting two more                   //因为在subscriber_2，在创建一个subscription，所以可以继续运行
    MainActivity: Subscriber received Item(id=5)
    MainActivity: Subscriber received Item(id=6)
    MainActivity: Done!
     */


    //https://www.jianshu.com/p/36c720697829

    inline fun Any.toIntOrError(): Int = toString().toInt()
    // 把任意的对象转化为数字, 肯定有转化不了的(比如"haha"), 此时 toIntOrError 会抛出异常
    // 这是 Kotlin 中的 `扩展方法` , 即, 可以给已有的类增加方法

    fun test8_1() {
        Observable.just(1, 2, "Errr", 3)  // 执行到 "Errr" 处会抛出异常, 那么 3 还会不会被传递下去呢?
            .map { it.toIntOrError() }
            .subscribe(observer)
    }
    /*
    New Subscription
    Next 1
    Next 2                                  // Next 3 没有被输出
    Error Occured For input string: "Errr"  // 执行了 onError 方法
     */


    //用一个指定值替换异常, 并且取消订阅
    fun test8_2() {
        Observable.just(1, 2, "Errr", 3)
            .map { it.toIntOrError() }
            .onErrorReturn { -1 }
            .subscribe(observer)
    }
    /*
    New Subscription
    Next 1
    Next 2
    Next -1             // 并没有输出 Next 3
    All Completed       // 执行了 observer 的 onComplete 方法
     */


    //在异常值处取消对原 Observable 的订阅, 并订阅另一个 Observable
    fun  test8_3() {
        Observable.just(1, 2, "Errr", 3)
            .map { it.toIntOrError() }
            .onErrorResumeWith (Observable.range(10, 2))  // 即订阅了 Observable.just(10,11)
            .subscribe(observer)
    }
    /*
    New Subscription
    Next 1
    Next 2
    Next 10             // 并没有输出 Next 3
    Next 11
    All Completed       // 执行了 observer 的 onComplete 方法
     */


    //上游有异常会重新订阅, 直到达到 times。如果仍然没有恢复，则向下游抛出最后一次订阅产生的异常
    fun  test8_4() {
        Observable.just(1, 2, "Errr", 3)
            .map { it.toIntOrError() }
            .retry(2)  //重新订阅 2 次
            .subscribe(observer)
    }
    /*
    New Subscription
    Next 1      \_    正常程序流程
    Next 2      /
    Next 1      \_    第一次重复尝试
    Next 2      /
    Next 1      \_    第二次重复尝试
    Next 2      /       // 两次还不行就向下游抛出最后一次订阅产生的异常
    Error Occured For input string: "Errr"
     */


    /*
    第一个参数: 目前已经重新订阅的次数
    第二个参数: 上游抛出的异常
    返回值: 如果为 true 则继续重新订阅, 否则, 向下游抛出最后一次订阅产生的异常
     */
    fun test8_5() {
        var retryCount = 0
        Observable.just(1, 2, "Errr", 3)
            .map { it.toIntOrError() }
            .retry { _, _ ->
                (++retryCount) < 3
            }
            .subscribe(observer)
    }
    /*
    New Subscription
    Next 1
    Next 2
    Next 1
    Next 2
    Next 1
    Next 2
    Error Occured For input string: "Errr"
     */
}