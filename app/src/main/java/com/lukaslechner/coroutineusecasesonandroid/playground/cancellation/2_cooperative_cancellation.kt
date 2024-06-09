package com.lukaslechner.coroutineusecasesonandroid.playground.cancellation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import kotlin.coroutines.cancellation.CancellationException

fun main() = runBlocking {
    val job = launch(Dispatchers.Default) {
//        countToTenEnsureActive()
//        countToTenYield()
        countToTenIsActive()
    }
    delay(300)
    println("Canceling job...")
    job.cancel()
}

suspend fun countToTenEnsureActive() = coroutineScope {
    repeat(10) {
        ensureActive()
        println("count: ${it + 1}")
        Thread.sleep(100)
    }
}
suspend fun countToTenYield() = coroutineScope {
    repeat(10) {
        yield()
        println("count: ${it + 1}")
        Thread.sleep(100)
    }
}
suspend fun countToTenIsActive() = coroutineScope {
    repeat(10) {
        if (!isActive) {
            println("Cleaning up, then throwing cancellation exception")
            throw CancellationException()
        }
        println("count: ${it + 1}")
        Thread.sleep(100)
    }
}