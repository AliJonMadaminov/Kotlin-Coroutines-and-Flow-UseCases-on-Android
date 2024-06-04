package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase5

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.HttpException

class NetworkRequestWithTimeoutViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest(timeout: Long) {
        performRequestWithTimeoutOrNull(timeout)
    }

    private fun performRequestWithTimeout(timeout: Long) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val recentAndroidVersions = withTimeout(timeout) { api.getRecentAndroidVersions() }
                uiState.value = UiState.Success(recentAndroidVersions)
            } catch (e: TimeoutCancellationException) {
                uiState.value = UiState.Error("Request timed out")
            } catch (e: HttpException) {
                uiState.value = UiState.Error("Network request failed")
            }
        }
    }

    private fun performRequestWithTimeoutOrNull(timeout: Long) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val recentAndroidVersions =
                    withTimeoutOrNull(timeout) { api.getRecentAndroidVersions() }
                if (recentAndroidVersions != null)
                    uiState.value = UiState.Success(recentAndroidVersions)
                else
                    uiState.value = UiState.Error("Request timed out")

            } catch (e: HttpException) {
                uiState.value = UiState.Error("Network request failed")
            }
        }
    }

}