package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.runBlocking

fun main() {

    val flow = flow {

        delay(100)
        emit(1)
        delay(100)
        emit(2)
        delay(100)
        emit(2)
        delay(100)
        emit(4)
    }

    runBlocking {
        val item = flow.toSet()
        println("Set: received $item")
    }
    runBlocking {
        val item = flow.toList()
        println("List: received $item")
    }
}