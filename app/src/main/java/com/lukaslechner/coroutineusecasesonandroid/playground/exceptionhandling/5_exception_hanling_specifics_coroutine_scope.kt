package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val exceptionHandler = CoroutineExceptionHandler { _, t ->
        println("Caught $t")
    }
    val scope = CoroutineScope(Dispatchers.Default + exceptionHandler)
    scope.launch {
        doSomethingSuspending()
    }
    delay(500)
}

private suspend fun coroutineScopeWithTryCatch() {
    try {
        doSomethingSuspending()
    } catch (e: Exception) {
        println("Caught $e")
    }
}

suspend fun doSomethingSuspending() = coroutineScope {
    launch {
        throw RuntimeException()
    }
}