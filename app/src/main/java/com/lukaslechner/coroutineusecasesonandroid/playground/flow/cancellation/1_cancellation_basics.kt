package com.lukaslechner.coroutineusecasesonandroid.playground.flow.cancellation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

suspend fun main() {
    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch {
        intFlow()
            .onCompletion { cause ->
                if (cause is CancellationException) {
                    println("Flow got cancelled")
                }
            }
            .collect {
                println("Collected $it")
                if (it == 2) cancel()
            }
    }.join()
}

private fun intFlow() = flow {
    emit(1)
    emit(2)
    emit(3)
}