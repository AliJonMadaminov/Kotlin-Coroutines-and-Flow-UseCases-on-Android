package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

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
        val item = flow.firstOrNull { it > 1 }
        println("Received $item")
    }
}

private suspend fun collectFirst(flow: Flow<Int>) {
    // only one emission occurs
    println("Received ${flow.firstOrNull()}")
}