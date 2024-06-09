package com.lukaslechner.coroutineusecasesonandroid.playground.cancellation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() = runBlocking {
    val job = launch(Dispatchers.Default) {
        repeat(10) {
            if (isActive) {
                println("count: ${it + 1}")
                Thread.sleep(100)
            } else {
                withContext(NonCancellable) {
                    delay(50)
                    println("Cleaning up...")
                    throw CancellationException()
                }
            }
        }
    }
    delay(300)
    println("Cancelling job...")
    job.cancel()
}