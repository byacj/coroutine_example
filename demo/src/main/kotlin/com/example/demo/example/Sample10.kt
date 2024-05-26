package com.example.demo.example

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()
    val participantDeferred1: Deferred<Array<String>> =
        async(Dispatchers.IO) {
            delay(1000L)
            return@async arrayOf("James","Jason")
        }

    val participantDeferred2: Deferred<Array<String>> =
        async(Dispatchers.IO) {
            delay(2000L)
            return@async arrayOf("Jenny")
        }

    val participants1 = participantDeferred1.await()
    val participants2 = participantDeferred2.await()

    println("[${getElapsedTime(startTime)}] 참여자 목록: " +
            "${listOf(*participants1, *participants2)}")
}