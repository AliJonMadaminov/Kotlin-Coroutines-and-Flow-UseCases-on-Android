package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

fun main() {

    // will not be executed
    val flow = flow {
        delay(100)
        println("Emitting first value")
        emit(1)
        delay(100)
        println("Emitting second value")
        emit(2)
    }

    // will be executed
    val list = buildList {
        println("adding 1 to list")
        add(1)
        println("adding 2 to list")
        add(2)
    }
}