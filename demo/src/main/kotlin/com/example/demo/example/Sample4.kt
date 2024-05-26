package com.example.demo.example

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    val whileJob: Job = launch(Dispatchers.Default) {
        while(true) {
            println("작업 중")
        }
    }
    whileJob.cancel() // 코루틴 취소
}
// 무한 로딩 함