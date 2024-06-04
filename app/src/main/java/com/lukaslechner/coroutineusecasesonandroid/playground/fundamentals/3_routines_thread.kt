package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import kotlin.concurrent.thread

fun main() {
    println("Main has started")
    threadRoutine(1, 500)
    threadRoutine(2, 300)
    Thread.sleep(1000)
    println("Main has finished")
}

private fun threadRoutine(number: Int, delay: Long) {
    thread {
        println("Routine $number has started on ${Thread.currentThread().name}")
        Thread.sleep(delay)
        println("Routine $number has finished")
    }
}