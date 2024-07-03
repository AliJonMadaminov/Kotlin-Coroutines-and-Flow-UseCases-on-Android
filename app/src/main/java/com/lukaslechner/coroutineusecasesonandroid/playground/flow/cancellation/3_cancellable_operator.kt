package com.lukaslechner.coroutineusecasesonandroid.playground.flow.cancellation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger
import kotlin.coroutines.cancellation.CancellationException

suspend fun main() {
    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch {
        flowOf(1, 2, 3, 4, 5)
            .onCompletion { cause ->
                if (cause is CancellationException) {
                    println("Flow got cancelled")
                }
            }
            .cancellable()
            .collect {
                println("collect: $it")
                if (it == 2) cancel()
            }
    }.join()
}