package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job

fun main() {
    println("Global job: ${GlobalScope.coroutineContext?.get(Job)}")
}