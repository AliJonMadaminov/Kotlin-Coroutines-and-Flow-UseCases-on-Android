package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {
    launch {
        stockFlow()
            .catch {
                println("Handle exception in catch{} operator")
            }
            .collect {
                println("Collect $it")
            }
    }
}

private fun stockFlow() = flow {
    repeat(5) { index ->
        delay(500) // network request
        if (index < 4) {
            emit("New stock data")
        } else {
            throw NetworkException("Network request failed")
        }
    }
}.retryWhen {cause, attempt ->
    delay(1000 * attempt) // retrying with delay, to give server time to recover
    println("Entered retry with cause: $cause")
    cause is NetworkException
}

class NetworkException(message: String) : Exception(message)