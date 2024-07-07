package com.lukaslechner.coroutineusecasesonandroid.playground.flow.concurrency

import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

suspend fun main(): Unit = coroutineScope {
    val flow = flow {
        repeat(10) { index ->
            println("Emitter: start cooking pancake $index")
            delay(100)
            println("Emitter: pancake is ready $index")
            emit(index)
        }
    }

    flow.collectLatest {
        println("Consumer: start eating pancake $it")
        delay(300)
        println("Consumer: finished eating pancake $it")
    }
}