package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase3

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PerformNetworkRequestsConcurrentlyViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        uiState.value = if (throwable is HttpException) {
            UiState.Error("Network request failed")
        } else {
            UiState.Error("Something went wrong")
        }
    }
    fun performNetworkRequestsSequentially() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val oreoFeatures = mockApi.getAndroidVersionFeatures(27)
                val pieFeatures = mockApi.getAndroidVersionFeatures(28)
                val android10Features = mockApi.getAndroidVersionFeatures(29)
                val versionFeatures = listOf(oreoFeatures, pieFeatures, android10Features)
                uiState.value = UiState.Success(versionFeatures)
            } catch (e: Exception) {
                uiState.value = UiState.Error("Network request failed")
            }
        }
    }

    fun performNetworkRequestsConcurrently() {
        uiState.value = UiState.Loading
        viewModelScope.launch(exceptionHandler) {
            val oreoFeatures = async { mockApi.getAndroidVersionFeatures(27) }
            val pieFeatures = async { mockApi.getAndroidVersionFeatures(28) }
            val android10Features = async { mockApi.getAndroidVersionFeatures(29) }
            try {
                val features = awaitAll(oreoFeatures, pieFeatures, android10Features)
                uiState.value = UiState.Success(features)
            } catch (e: HttpException) {
                uiState.value = UiState.Error("Network request failed")
            }
        }
    }
}