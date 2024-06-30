package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermediate_operators

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOf

suspend fun main() {
    flowOf(1, 2, null, 3, 3.5f, 4, 5)
        .filterNotNull()
        .filterIsInstance<Int>()
        .printEvenNumbers()
        .printOddNumbers()
}

private suspend fun Flow<Int>.printEvenNumbers(): Flow<Int> {
    filter { it % 2 == 0 }.collect { print("$it ") }
    println()
    return this
}

private suspend fun Flow<Int>.printOddNumbers(): Flow<Int> {
    filterNot { it % 2 == 0 }.collect { print("$it ") }
    println()
    return this
}