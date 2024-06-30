package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermediate_operators

import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.withIndex
import kotlin.math.roundToInt

suspend fun main() {
    flowOf(1, 2, 2, 3, 3, 4, 5, 2)
        // prevents consecutive emissions of the same value
        .distinctUntilChanged()
        .collect {
            println(it)
        }
}