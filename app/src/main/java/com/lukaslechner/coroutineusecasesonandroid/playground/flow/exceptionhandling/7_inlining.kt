package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

suspend fun main(): Unit = coroutineScope {
    flow {
        emit(1) // collect(1)
        emit(2) // collect(2)
        emit(3) // collect(3)
    }.collect {
        println("Collected $it")
    }
}

private val inlinedFLow = flow<Int> {
    println("Collected 1")
    println("Collected 2")
    println("Collected 3")
}