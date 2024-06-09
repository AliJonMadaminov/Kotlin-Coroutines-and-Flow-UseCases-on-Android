package com.lukaslechner.coroutineusecasesonandroid.playground.cancellation

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException

fun main() = runBlocking {
    val job = launch {
        repeat(10) {
            println("Operation number: $it")
            try {
                delay(100)
            } catch (e: CancellationException) {
                println("Cancellation exception was thrown")
                throw e
            }
        }
    }
    delay(250)
    println("Canceling coroutine")
    job.cancel()
}