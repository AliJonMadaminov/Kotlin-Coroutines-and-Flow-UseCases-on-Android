package com.lukaslechner.coroutineusecasesonandroid.playground.flow.cancellation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger
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
    println("Started Calculation")
    calculateFactorialOf(100)
    println("Ended Calculation")
    emit(3)
}

private suspend fun calculateFactorialOf(number: Int) = coroutineScope {
    var factorial = BigInteger.ONE
    for (i in 1..number) {
        ensureActive()
        factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
    }
    factorial
}