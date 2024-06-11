package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun main() {

    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch {
        try {
            // exception is not caught
            launch { functionThatThrowsException() }
        } catch (e: Exception) {
            println("Caught: $e")
        }
    }
    Thread.sleep(500)
}

private fun functionThatThrowsException() {
    throw RuntimeException()
}