package com.example.demo.example

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    val longJob: Job = launch(context = Dispatchers.Default, start = CoroutineStart.LAZY) {
        println("launch 수행")
    }
    longJob.cancel() // longJob 취소 요청
    executeAfterJobCancelled2() // 코루틴 취소 후 실행돼야 하는 동작
}

fun executeAfterJobCancelled2() {
    println("cancel하고 수행")
}