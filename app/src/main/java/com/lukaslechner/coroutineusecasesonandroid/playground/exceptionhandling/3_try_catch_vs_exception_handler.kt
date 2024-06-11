package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    independentCoroutinesWithCoroutineExceptionHandler()
//    dependentCoroutinesWithCoroutineExceptionHandler()
}

private fun dependentCoroutinesWithCoroutineExceptionHandler() {
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("An error occurred: $exception")
    }
    val scope = CoroutineScope(Job() + exceptionHandler)

    scope.launch {

        launch {
            println("Starting 1 coroutine")
            delay(500)
            throw RuntimeException()
        }

        launch {
            println("Starting 2 coroutine")
            delay(600)
            println("Ending 2 coroutine")
        }
    }

    Thread.sleep(1000)
}
private fun independentCoroutinesWithCoroutineExceptionHandler() {
    val scope = CoroutineScope(Job())

    scope.launch {

        launch {
            println("Starting 1 coroutine")
            delay(500)
            try {
                throw RuntimeException()
            } catch (e: Exception) {
                println("An error occurred: $e")
            }
        }

        launch {
            println("Starting 2 coroutine")
            delay(600)
            println("Ending 2 coroutine")
        }
    }

    Thread.sleep(1000)
}