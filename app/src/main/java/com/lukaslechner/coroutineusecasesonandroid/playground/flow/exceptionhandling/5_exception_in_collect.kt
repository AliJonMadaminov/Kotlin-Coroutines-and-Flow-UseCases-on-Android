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
    val stocksFlow = stocksFlow()

    stocksFlow
        .onCompletion { cause ->
            if (cause == null) {
                println("Flow completed successfully")
            } else {
                println("Flow completed with error: $cause")
            }
        }
        .onEach {
            println("Collected $it")
            throw Exception("Exception in collect block")
        }
        .catch { throwable ->
            println("Handling exception in catch() operator $throwable")
        }
        .launchIn(this)
}

private fun stocksFlow() = flow {
    emit("Apple")
    emit("Google")
    emit("Facebook")
    throw Exception("Network request failed")
}