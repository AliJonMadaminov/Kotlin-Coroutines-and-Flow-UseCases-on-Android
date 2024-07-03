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
    properExceptionTransparency()
}

private suspend fun brokenExceptionTransparency() {
    flow {
        try {
            emit(1)
        } catch (e: Exception) {
            /*
            exception in collect block is caught
            because under the hood emit(1) calls collect(1)
             */
            println("Caught exception in flow{} builder $e")
        }
    }.collect {
        println("Collected $it")
        throw Exception("exception in collect block")
    }
}

private suspend fun properExceptionTransparency() {
    flow {
        emit(1)
    }
        .catch {
            println("Caught exception in flow{} builder $it")
        }
        .collect {
            println("Collected $it")
            throw Exception("exception in collect block")
        }
}