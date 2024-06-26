package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operators

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

fun main() {

    val flow = flow {

        delay(100)
        println("Emitting first value")
        emit("One")
        delay(100)
        println("Emitting second value")
        emit("Two")
    }
    val scope = CoroutineScope(Dispatchers.Default)
    // LaunchIn usage
    launchInUsage(flow, scope)

    // Concurrent(non-suspending) usage
    concurrentFlowUsage(flow, scope)

    sequentialFlowUsage(scope, flow)

    Thread.sleep(1000)
}

private fun launchInUsage(
    flow: Flow<String>,
    scope: CoroutineScope
) {
    flow
        .onStart { println("OnStart") }
        .onEach { println("OnEach $it") }
        .onCompletion { println("OnCompletion") }
        .launchIn(scope)
}

private fun concurrentFlowUsage(
    flow: Flow<String>,
    scope: CoroutineScope
) {
    flow.onEach { println("OnEach $it with launchIn 1") }.launchIn(scope)
    flow.onEach { println("OnEach $it with launchIn 2") }.launchIn(scope)
}

private fun sequentialFlowUsage(
    scope: CoroutineScope,
    flow: Flow<String>
) {
    scope.launch {
        flow.collect { println("OnEach $it with collect 1") }
        flow.collect { println("OnEach $it with collect 2") }
    }
}
