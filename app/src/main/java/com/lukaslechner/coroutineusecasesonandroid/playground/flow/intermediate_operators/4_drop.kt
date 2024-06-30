package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermediate_operators

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.takeWhile

suspend fun main() {
    listOf(1, 2, 3, 4, 5).asFlow()
        .drop(2)
        .dropWhile {
            it < 5
        }
        .collect { println(it) }
}