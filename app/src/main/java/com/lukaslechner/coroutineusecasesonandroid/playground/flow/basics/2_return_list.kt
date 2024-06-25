package com.lukaslechner.coroutineusecasesonandroid.playground.flow.basics

import com.lukaslechner.coroutineusecasesonandroid.playground.utils.printWithTimePassed
import java.math.BigInteger

fun main() {
    val startTime = System.currentTimeMillis()
    val result = calculateFactorialOf(5).forEach {
        printWithTimePassed(it, startTime)
    }
}

private fun calculateFactorialOf(number: Int): List<BigInteger> = buildList{
    var result = BigInteger.ONE
    for (i in 1..number) {
        Thread.sleep(10)
        result = result.multiply(BigInteger.valueOf(i.toLong()))
        add(result)
    }
}