package com.lukaslechner.coroutineusecasesonandroid.playground.flow.hot_and_cold_flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {
    launch {
        coldFlow()
            .collect {
                println("Collector 1 collects $it")
            }
    }
    launch {
        coldFlow()
            .collect {
                println("Collector 2 collects $it")
            }
    }
}

private suspend fun flowStartsEmittingWhenCollectedAndStopsWhenCoroutineGotCancelled() =
    coroutineScope {
        val job = launch {
            coldFlow()
                .onCompletion {
                    println("Flow has completed")
                }
                .collect {
                    println("Collected $it")
                }
        }
        delay(1_500)
        job.cancelAndJoin()
    }

fun coldFlow() = flow {
    println("emitting 1")
    emit(1)

    delay(1000)
    println("emitting 2")
    emit(2)

    delay(1000)
    println("emitting 3")
    emit(3)
}