package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.cancellation.CancellationException

fun main() = runBlocking {
    cancelingChildDoesNotCancelParentAndSiblings()
}

private suspend fun cancelingParentCancelsChildren() {
    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch {
        delay(1000)
        println("Coroutine 1 has completed")
    }.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine 1 cancelled")
        }
    }
    scope.launch {
        delay(1000)
        println("Coroutine 2 has completed")
    }.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine 2 cancelled")
        }
    }

    delay(500)
    scope.cancel()
    println("Scope job cancelled")
}

private suspend fun cancelingChildDoesNotCancelParentAndSiblings() {
    val scope = CoroutineScope(Dispatchers.Default)
    val childJob = scope.launch {
        delay(1000)
        println("Coroutine 1 has completed")
    }
    childJob.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine 1 cancelled")
        }
    }
    scope.launch {
        delay(1000)
        println("Coroutine 2 has completed")
    }.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine 2 cancelled")
        }
    }
    scope.coroutineContext.job.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Parent job cancelled")
        }
    }
    childJob.cancelAndJoin()
    delay(1000)
}