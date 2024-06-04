package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import kotlinx.coroutines.async
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("Main has started")
    joinAll(
        async { coroutineWithThreadInfo(1, 500) },
        async { coroutineWithThreadInfo(2, 300) }
    )

    println("Main has finished")
}

suspend fun coroutineWithThreadInfo(number: Int, delay: Long) {
    println("Coroutine $number has started on ${Thread.currentThread().name}")
    delay(delay)
    println("Coroutine $number has finished")
}