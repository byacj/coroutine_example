package com.example.demo.example

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalStdlibApi::class)
fun main() = runBlocking<Unit> {
    val coroutineContext: CoroutineContext = newSingleThreadContext("MyThread") + CoroutineName("MyCoroutine")

    launch(context = coroutineContext) {
        println("[${Thread.currentThread().name}] 실행")
        println("name : ${this.coroutineContext[CoroutineName.Key]}")
        println("job : ${this.coroutineContext[Job.Key]}")
        println("coroutineDispatcher : " +
                "${this.coroutineContext[CoroutineDispatcher.Key]}")
        println("coroutineExceptionHandler : ${this.coroutineContext[CoroutineExceptionHandler.Key]}")

    }
}

// [MyThread @MyCoroutine#2] 실행