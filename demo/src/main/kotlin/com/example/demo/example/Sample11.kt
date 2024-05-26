package com.example.demo.example

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()
    val participantDeferred1: Deferred<Array<String>> = async(Dispatchers.IO) {
        delay(1000L)
        arrayOf("James","Jason")
    }

    val participantDeferred2: Deferred<Array<String>> = async(Dispatchers.IO) {
        delay(2000L)
        arrayOf("Jenny")
    }

    // 요청이 끝날 때까지 대기
    val results: List<Array<String>> = awaitAll(participantDeferred1, participantDeferred2)

    println("[${getElapsedTime(startTime)}] 참여자 목록: ${listOf(*results[0], *results[1])}")
}