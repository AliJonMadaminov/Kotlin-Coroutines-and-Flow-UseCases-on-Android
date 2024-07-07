package com.lukaslechner.coroutineusecasesonandroid.playground.flow.concurrency

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest

suspend fun main(): Unit = coroutineScope {
    val flow = flow {
        repeat(5) { index ->
            println("Emitter: start cooking pancake $index")
            delay(100)
            println("Emitter: pancake is ready $index")
            emit(index)
        }
    }.conflate()

    flow.collect {
        println("Consumer: start eating pancake $it")
        delay(300)
        println("Consumer: finished eating pancake $it")
    }
}