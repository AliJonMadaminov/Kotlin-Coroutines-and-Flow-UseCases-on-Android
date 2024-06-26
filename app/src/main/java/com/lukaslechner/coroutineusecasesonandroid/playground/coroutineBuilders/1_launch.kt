package com.lukaslechner.coroutineusecasesonandroid.playground.coroutineBuilders

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val job = launch(start = CoroutineStart.LAZY) {
        delay(500)
        println("result received")
    }
    job.start()
    println("end of runBlocking")
}