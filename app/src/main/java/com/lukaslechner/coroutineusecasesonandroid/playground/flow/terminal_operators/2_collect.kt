package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

fun main() {

    val flow = flow {
        println("Flow builder's coroutine context: ${coroutineContext}")

        delay(100)
        println("Emitting first value")
        emit(1)
        delay(100)
        println("Emitting second value")
        emit(2)
    }

    runBlocking {
        println("Flow collector's coroutine context: ${this.coroutineContext}")
        flow.collect {
            println("Received $it")
        }
    }
}