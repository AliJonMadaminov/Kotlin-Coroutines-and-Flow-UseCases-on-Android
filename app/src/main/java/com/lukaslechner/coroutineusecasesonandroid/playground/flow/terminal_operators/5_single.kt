package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.runBlocking

fun main() {

    val flow = flow {

        delay(100)
        println("Emitting first value")
        emit(1)
        delay(100)
//        println("Emitting second value")
//        emit(2)
    }

    runBlocking {
        // Exactly one value should be emitted
        val item = flow.singleOrNull()
        println("Received $item")
    }
}