package com.example.demo.example

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()
    val participantDeferred1: Deferred<Array<String>> = async(Dispatchers.IO) {
        delay(1000L)
        arrayOf("James","Jason")
    }

//    val participantDeferred2: Deferred<Array<String>> = async(Dispatchers.IO) {
//        delay(2000L)
//        1565
//    }
//
//    val results: List<Array<String>> =
//        awaitAll(participantDeferred1, participantDeferred2)
//
//    println("[${getElapsedTime(startTime)}] 참여자 목록: ${listOf(*results[0], *results[1])}")
}