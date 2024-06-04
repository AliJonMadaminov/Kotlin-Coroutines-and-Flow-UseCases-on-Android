package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

fun main() {
    println("Main has started")
    routine(1, 500)
    routine(2, 300)
    println("Main has finished")
}

private fun routine(number: Int, delay: Long) {
    println("Routine $number has started")
    Thread.sleep(delay)
    println("Routine $number has finished")
}