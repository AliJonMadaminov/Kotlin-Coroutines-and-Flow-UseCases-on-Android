package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val scopeJob = Job()
    val scope = CoroutineScope(Dispatchers.Default + scopeJob)
    val passedJob = Job()
    val coroutineJob = scope.launch(passedJob) {
        delay(1000)
        println("Starting coroutine")
    }
    delay(500)
    println("coroutine job is a passed job => ${coroutineJob === passedJob}")
    println("coroutine job is a child of passed job => ${passedJob.children.contains(coroutineJob)}")
    println("passed job is a child of scope job => ${scopeJob.children.contains(passedJob)}")
    println("is coroutineJob is a child of scopeJob => ${scopeJob.children.contains(coroutineJob)}")
    scope.cancel()
    println("scope is cancelled")
    delay(1000)
}