package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion

suspend fun main() = coroutineScope {
    val stocksFlow = stocksFlow()
        .map { println("Mapped $it") }

    stocksFlow
        .onCompletion { cause ->
            if (cause == null) {
                println("Flow completed successfully")
            } else {
                println("Flow completed with error: $cause")
            }
        }
        .catch { throwable -> println("Caught $throwable") }
        .collect {
            println("Collected $it")
        }
}

private fun stocksFlow() = flow {
    emit("Apple")
    emit("Google")
    emit("Facebook")
    throw Exception("Network request failed")
}