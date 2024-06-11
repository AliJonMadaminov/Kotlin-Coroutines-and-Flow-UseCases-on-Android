package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun main() {
//    properUseOfExceptionHandler()
    wrongUseOfExceptionHandler()
}

private fun properUseOfExceptionHandler() {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Exception handled: $throwable")
    }


    val scope = CoroutineScope(Job() + exceptionHandler)

    scope.launch {
        throw RuntimeException()
    }

    Thread.sleep(500)
}
private fun wrongUseOfExceptionHandler() {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Exception handled: $throwable")
    }


    val scope = CoroutineScope(Job())

    scope.launch {
        launch(exceptionHandler) {
            throw RuntimeException()
        }
    }

    Thread.sleep(500)
}