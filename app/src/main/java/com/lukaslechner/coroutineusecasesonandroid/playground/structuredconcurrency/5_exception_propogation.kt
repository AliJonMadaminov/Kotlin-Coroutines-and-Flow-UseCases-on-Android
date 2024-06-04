package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

fun main() {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Caught $throwable")
    }
    /*
    If Job() is passed, fail of child will cancel siblings and parent.
    Exception will be propagated up.

    If SupervisorJob() is passed, fail of child will not cancel siblings and parent.
     */
    val scope = CoroutineScope(Job() + exceptionHandler)
    scope.launch {
        println("Coroutine 1 started")
        delay(100)
        println("Coroutine 1 failed")
        throw RuntimeException()
    }

    scope.launch {
        println("Coroutine 2 started")
        delay(500)
        println("Coroutine 2 finished")
    }.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine 2 cancelled")
        }
    }

    Thread.sleep(1000)
    println("scope active: ${scope.isActive}")
}