package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.runBlocking

fun main() {

    val flow = flow {

        delay(100)
        println("Emitting first value")
        emit(1)
        delay(100)
        println("Emitting second value")
        emit(2)
    }

    runBlocking {
        val item = flow.lastOrNull()
        println("Received $item")
    }
}

private suspend fun collectLast(flow: Flow<Int>) {
    // all emissions occur but last is received
    println("Received ${flow.lastOrNull()}")
}