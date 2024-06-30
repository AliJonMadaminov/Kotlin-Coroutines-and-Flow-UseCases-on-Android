package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermediate_operators

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.takeWhile

suspend fun main() {
    listOf(1, 2, 3, 4, 5).asFlow()
        .take(4)
        // unlike filter takeWhile cancels the flow when the condition is false
        .takeWhile { it <= 3 }
        .collect { println(it) }
}