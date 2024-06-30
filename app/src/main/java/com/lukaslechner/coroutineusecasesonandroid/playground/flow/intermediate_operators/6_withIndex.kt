package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermediate_operators

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.withIndex
import kotlin.math.roundToInt

suspend fun main() {
    flowOf(1.5f, 2.9f, 3f, 4f, 5.2f)
        // adds index to each element
        .withIndex()
        .collect {
            println("Index: ${it.index}, Value: ${it.value}")
        }
}