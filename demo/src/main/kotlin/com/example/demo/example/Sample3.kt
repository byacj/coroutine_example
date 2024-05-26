package com.example.demo.example

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job1: Job = launch(start = CoroutineStart.LAZY) {} // 생성 상태의 Job 생성
    val job2: Job = launch {} // 생성과 동시에 실행
    val job3: Job = launch {launch { delay(1000L) }}  // 생성과 동시 실행. 자식 코루틴이 실행중
    delay(500)
    printJobState("job1", job1)
    printJobState("job2", job2)
    printJobState("job3", job3)
}
fun printJobState(name : String, job: Job) {
    println(
        "${name} State\n" +
                "isActive >> ${job.isActive}\n" +
                "isCancelled >> ${job.isCancelled}\n" +
                "isCompleted >> ${job.isCompleted} "
    )
}