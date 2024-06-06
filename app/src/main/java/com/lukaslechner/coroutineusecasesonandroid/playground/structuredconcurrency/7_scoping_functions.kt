package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Caught $throwable")
    }
    val scope = CoroutineScope(Job() + exceptionHandler + CoroutineName("RootScopeName"))
    println(scope.coroutineContext)
    scope.launch {
        performTasks(3)
        launch {
            println("Doing some other work")
            delay(100)
            println("Ending some other work")
        }
    }
    Thread.sleep(500)
}

private suspend fun performTasks(numberOfTasks: Int) = coroutineScope {
    repeat(numberOfTasks) {
        launch {
            println("Started task $it")
            delay(100)
            println("Ended task $it")
        }
    }
    println("Inside performTasks " + this.coroutineContext)
}

private suspend fun performTasks() = coroutineScope {
    launch {
        println("Started task 0")
        delay(50)
        throw Exception("Task 0 failed exception")
        println("Ended task 0")
    }
    launch {
        if (!isActive) return@launch
        println("Started task 1")
        delay(100)
        println("Ended task 1")
    }.invokeOnCompletion {
        if (it is CancellationException) {
            println("Task 1 was cancelled")
        }
    }
}