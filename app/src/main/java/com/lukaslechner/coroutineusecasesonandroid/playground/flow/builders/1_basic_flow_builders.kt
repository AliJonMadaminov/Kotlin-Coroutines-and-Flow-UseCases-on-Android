package com.lukaslechner.coroutineusecasesonandroid.playground.flow.builders

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

suspend fun main() {
    val firstFlow = flowOf(1, 2, 3)

    firstFlow.collect {
        println("flowOf: $it")
    }

    listOf("One", "Two", "Three").asFlow().collect {
        println("asFlow: $it")
    }

    flow {
        delay(1000)
        emit("Emitted after 1000ms delay")
        emitAll(firstFlow)
    }.collect {
        println("flow{}: $it")
    }
}