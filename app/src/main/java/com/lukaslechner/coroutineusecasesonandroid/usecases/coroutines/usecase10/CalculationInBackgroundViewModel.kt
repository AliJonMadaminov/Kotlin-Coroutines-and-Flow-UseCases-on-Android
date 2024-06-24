package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase10

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.math.BigInteger
import kotlin.system.measureTimeMillis

class CalculationInBackgroundViewModel(private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default) : BaseViewModel<UiState>() {

    fun performCalculation(factorialOf: Int) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            Timber.tag("DispatcherCheckTag").d("currentCoroutineContext: $coroutineContext")
            var result: BigInteger
            val computationDuration = measureTimeMillis {
                result = calculateFactorialOf(factorialOf)
            }

            var resultString: String
            val stringConversionDuration = measureTimeMillis {
                resultString = withContext(defaultDispatcher + CoroutineName("Akang qarag'ay coroutine name berishni o'rgandi")) {
                    Timber.tag("DispatcherCheckTag").d("insideStringConversion: $coroutineContext")
                    result.toString()
                }
            }
            uiState.value =
                UiState.Success(resultString, computationDuration, stringConversionDuration)
        }
    }

    private suspend fun calculateFactorialOf(number: Int): BigInteger {
        return withContext(defaultDispatcher) {
            var factorial = BigInteger.ONE
            for (i in 1..number) {
                factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
            }
            factorial
        }
    }
}