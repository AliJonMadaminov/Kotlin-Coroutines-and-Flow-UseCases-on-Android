package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("Main has started")
    joinAll(
        async { coroutine(1, 500) },
        async { coroutine(2, 300) }
    )

    println("Main has finished")
}

suspend fun coroutine(number: Int, delay: Long) {
    println("Coroutine $number has started")
    delay(delay)
    println("Coroutine $number has finished")
}