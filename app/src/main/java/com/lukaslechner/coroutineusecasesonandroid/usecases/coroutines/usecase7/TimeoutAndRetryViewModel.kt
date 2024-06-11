package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase7

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase6.withRetry
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.SupervisorJob
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
            retryWithTimeoutRequests(timeout, numberOfRetries)
        }

        // switch to branch "coroutine_course_full" to see solution

        // run api.getAndroidVersionFeatures(27) and api.getAndroidVersionFeatures(28) in parallel

    }

    private suspend fun retryWithTimeoutRequests(timeout: Long, numberOfRetries: Int) =
        supervisorScope {
            val result1 = async {
                retryWithTimeout(timeout, numberOfRetries) { api.getAndroidVersionFeatures(27) }
            }
            val result2 = async {
                retryWithTimeout(timeout, numberOfRetries) { api.getAndroidVersionFeatures(28) }
            }
            launch {
                val version = mutableListOf<VersionFeatures>()
                result1.awaitOrNull()?.let { version.add(it) }
                result2.awaitOrNull()?.let { version.add(it) }
                uiState.value = UiState.Success(version)
            }
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

    private suspend fun <T> Deferred<T>.awaitOrNull(): T? {
        return try {
            await()
        } catch (e: Exception) {
            null
        }
    }
}