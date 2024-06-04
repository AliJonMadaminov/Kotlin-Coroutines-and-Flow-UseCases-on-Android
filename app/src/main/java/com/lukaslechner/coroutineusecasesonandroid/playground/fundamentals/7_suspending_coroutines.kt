package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import kotlinx.coroutines.async
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    println("Main has started")
    joinAll(
        async { suspendingCoroutine(1, 500) },
        async { suspendingCoroutine(2, 300) },
        async {
            repeat(5) {
                println("Other tasks are running on ${Thread.currentThread().name}")
                delay(100)
            }
        }
    )

    println("Main has finished")
}

suspend fun suspendingCoroutine(number: Int, delay: Long) {
    println("Coroutine $number has started on ${Thread.currentThread().name}")
    delay(delay)
    println("Coroutine $number has finished")
}