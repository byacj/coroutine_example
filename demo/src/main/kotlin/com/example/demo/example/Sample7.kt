package com.example.demo.example

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> { // this: CoroutineScope
    println("[${Thread.currentThread().name}] 실행1")
    launch (context = Dispatchers.Default){
        println("[${Thread.currentThread().name}] 실행2")
    }
    launch {
        println("[${Thread.currentThread().name}] 실행3")
    }
}