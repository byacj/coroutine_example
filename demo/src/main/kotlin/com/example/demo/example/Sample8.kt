package com.example.demo.example

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    val networkReadyJob = launch(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] 통신 준비!")
        delay(100L)
        println("[${Thread.currentThread().name}] 통신 준비 완료!")
    }
    val job = launch(Dispatchers.IO) {
        delay(300L)
        println("[${Thread.currentThread().name}] 통신아닌 작업")
    }
    networkReadyJob.join()
    val networkCallJob = launch(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] 네트워크 요청")
    }
}