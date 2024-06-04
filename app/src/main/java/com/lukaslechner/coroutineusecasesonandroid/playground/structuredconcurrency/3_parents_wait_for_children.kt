package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val scope = CoroutineScope(Dispatchers.Default)
    val parentCoroutine = scope.launch {
        launch {
            delay(500)
            println("Child coroutine 1 completed")
        }
        launch {
            delay(500)
            println("Child coroutine 2 completed")
        }
    }
    parentCoroutine.join()
    println("ParentJobCompleted")
}