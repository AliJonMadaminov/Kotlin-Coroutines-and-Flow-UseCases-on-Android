package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase6

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber

class RetryNetworkRequestViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            val numberOfRetries = 2
            try {
                val recentVersions = withRetry(numberOfRetries, maxDelayMillis = 5_000) { api.getRecentAndroidVersions() }
                uiState.value = UiState.Success(recentVersions)
            } catch (e: HttpException) {
                uiState.value = UiState.Error("Network request failed")
            }
        }
    }

    private suspend fun loadVersions() {
        val recentAndroidVersions = api.getRecentAndroidVersions()
        uiState.value = UiState.Success(recentAndroidVersions)
    }
}

suspend fun <T> withRetry(
    times: Int,
    initialDelayMillis: Long = 1_500,
    maxDelayMillis: Long = 2_000,
    delayFactor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelayMillis
    repeat(times) { i ->
        try {
            return block()
        } catch (e: Exception) {
            Timber.e(e)
        }
        delay(currentDelay)
        Timber.tag("RetryDelayTag").d("iteration: $i, delay: $currentDelay")
        currentDelay = (initialDelayMillis * delayFactor).toLong().coerceAtMost(maxDelayMillis)
    }
    return block()
}