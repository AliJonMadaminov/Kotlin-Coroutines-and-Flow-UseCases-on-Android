package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

fun main() {
    val scope = CoroutineScope(Dispatchers.Default)
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("CoroutineExceptionHandler got $throwable")
    }
    scope.launch(exceptionHandler) {
        supervisorScope {
            launch {
                throw RuntimeException()
            }
        }
    }
    Thread.sleep(300)
}