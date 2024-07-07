package com.lukaslechner.coroutineusecasesonandroid.playground.flow.concurrency

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

suspend fun main(): Unit = coroutineScope {
    val flow = MutableStateFlow(0)

    launch {
        val timeToEmit = measureTimeMillis {
            repeat(5) {
                flow.emit(it)
                delay(10)
            }
        }
        println("time to emit $timeToEmit")
    }

    launch {
        flow.collect {
            println("collector 1 processed $it")
        }
    }
    launch {
        flow.collect {
            delay(100)
            println("collector 2 processed $it")
        }
    }
}