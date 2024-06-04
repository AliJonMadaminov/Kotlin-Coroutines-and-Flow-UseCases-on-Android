package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase7

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase6.withRetry
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withTimeout

class TimeoutAndRetryViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        uiState.value = UiState.Loading
        val numberOfRetries = 2
        val timeout = 1000L
        viewModelScope.launch {
            supervisorScope {
                val result1 = async {
                    retryWithTimeout(timeout, numberOfRetries) { api.getAndroidVersionFeatures(27) }
                }
                val result2 = async {
                    retryWithTimeout(timeout, numberOfRetries) { api.getAndroidVersionFeatures(28) }
                }
                try {
                    uiState.value = UiState.Success(listOf(result1, result2).awaitAll())
                } catch (e: Exception) {
                    uiState.value = UiState.Error("Network request failed withing supervisor scope")
                }
            }
        }

        // switch to branch "coroutine_course_full" to see solution

        // run api.getAndroidVersionFeatures(27) and api.getAndroidVersionFeatures(28) in parallel

    }

    private suspend fun <T> retryWithTimeout(
        timeout: Long,
        numberOfRetries: Int,
        block: suspend () -> T
    ): T {
        return withRetry(numberOfRetries) {
            withTimeout(timeout) {
                block()
            }
        }
    }
}