package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermediate_operators

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform
import kotlin.math.roundToInt

suspend fun main() {
    flowOf(1.5f, 2.9f, 3f, 4f, 5.2f)
        // Unlike map, transform can be used to emit multiple values
        .transform {
            emit(it)
            emit(it.roundToInt())
        }
        .collect { println(it) }
}