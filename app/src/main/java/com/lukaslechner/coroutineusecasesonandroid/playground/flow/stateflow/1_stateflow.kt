package com.lukaslechner.coroutineusecasesonandroid.playground.flow.stateflow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

suspend fun main() {
    val counter = MutableStateFlow(0)
    println("Counter: ${counter.value}")
    coroutineScope {
        repeat(10_000) {
            launch {
                counter.update { currentValue -> currentValue + 1 }
            }
        }
    }
    println("Counter: ${counter.value}")
}