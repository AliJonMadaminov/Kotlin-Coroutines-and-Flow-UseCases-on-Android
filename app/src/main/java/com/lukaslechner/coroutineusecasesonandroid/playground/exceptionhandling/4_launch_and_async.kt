package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
//    catchingExceptionWhileAwaiting()
//    asyncInsideLaunchExceptionPropagated()
    asyncInsideAsyncExceptionStoredInTopLevelDeferred()
}

private suspend fun catchingExceptionWhileAwaiting() {
    val scope = CoroutineScope(Job())

    val deferred = scope.async {
        delay(300)
        throw RuntimeException()
    }
    try {
        deferred.await()
    } catch (e: Exception) {
        println("Caught exception while awaiting $e")
    }
    Thread.sleep(500)
}

private suspend fun asyncInsideLaunchExceptionPropagated() {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Caught $throwable")
    }
    val scope = CoroutineScope(Job() + exceptionHandler)

    scope.launch {
        async {
            delay(300)
            throw RuntimeException()
        }
    }
    Thread.sleep(500)
}
private suspend fun asyncInsideAsyncExceptionStoredInTopLevelDeferred() {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Caught $throwable")
    }
    val scope = CoroutineScope(Job() + exceptionHandler)

    scope.async {
        async {
            delay(300)
            throw RuntimeException()
        }
    }
    Thread.sleep(500)
}