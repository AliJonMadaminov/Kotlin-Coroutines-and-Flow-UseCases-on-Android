package com.lukaslechner.coroutineusecasesonandroid.playground.flow.hot_and_cold_flows

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {
    val sharedFlow = MutableSharedFlow<Int>()
//    hotFlowsEmitValueEvenWhenThereIsNoCollector(sharedFlow)
    multipleCollectorsReceiveSameEmissions(sharedFlow)
}

private suspend fun hotFlowsEmitValueEvenWhenThereIsNoCollector(sharedFlow: MutableSharedFlow<Int>) =
    coroutineScope {
        launch {
            repeat(5) { index ->
                println("Shared flow emits $index")
                sharedFlow.emit(index)
                delay(500)
            }
        }
        
        delay(1_500)
        
        launch {
            sharedFlow.collect { value ->
                println("Shared flow collects $value")
            }
        }
    }
private suspend fun multipleCollectorsReceiveSameEmissions(sharedFlow: MutableSharedFlow<Int>) =
    coroutineScope {
        launch {
            repeat(5) { index ->
                println("Shared flow emits $index")
                sharedFlow.emit(index)
                delay(500)
            }
        }
        
        delay(1_500)
        
        launch {
            sharedFlow.collect { value ->
                println("Collector 1 collects $value")
            }
        }
        launch {
            sharedFlow.collect { value ->
                println("Collector 2 collects $value")
            }
        }
    }