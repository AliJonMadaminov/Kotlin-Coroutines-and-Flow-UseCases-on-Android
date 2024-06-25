package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.runBlocking

fun main() {
//    sumOfEmissionsWithReduce()
    shoppingCartCaseWithFold()
}

private fun sumOfEmissionsWithReduce() {
    val flow = flow {

        delay(100)
        emit(1)
        delay(100)
        emit(2)
        delay(100)
        emit(3)
        delay(100)
        emit(4)
    }

    runBlocking {
        // Sum of emissions
        val item = flow.reduce { acc, value ->
            acc + value
        }
        println("received $item")
    }
}

private fun shoppingCartCaseWithFold() {
    val shoppingCartFlow: Flow<ShoppingItem> = flow {
        delay(100)
        emit(ShoppingItem(name = "Milk", price = 3))
        delay(100)
        emit(ShoppingItem(name = "Bread", price = 1, quantity = 2))
    }

    runBlocking {
        val cheque = shoppingCartFlow.fold("") { accumulator, value ->
            "$accumulator\n${value.name}\tquantity:${value.quantity}\tprice:${value.price * value.quantity} USD"
        }
        println(cheque)
    }
}

private data class ShoppingItem(val name: String, val price: Int, val quantity: Int = 1)