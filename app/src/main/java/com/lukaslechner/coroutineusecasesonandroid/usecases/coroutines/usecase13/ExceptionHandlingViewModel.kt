package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase13

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber

class ExceptionHandlingViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun handleExceptionWithTryCatch() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val result = api.getAndroidVersionFeatures(27)
            } catch (e: Exception) {
                uiState.value = UiState.Error("Network request failed: $e")
            }
        }
    }

    fun handleWithCoroutineExceptionHandler() {
        uiState.value = UiState.Loading
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            uiState.value = when (throwable) {
                is HttpException -> UiState.Error("Network request failed(HttpException)")
                else -> UiState.Error("Something went wrong")
            }
        }
        viewModelScope.launch(exceptionHandler) {
            api.getAndroidVersionFeatures(27)
        }
    }

    fun showResultsEvenIfChildCoroutineFails() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            val versionFeatures = getVersionFeaturesDeferred().mapNotNull {
                try {
                    it.await()
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    println("Error loading features!")
                    null
                }
            }
            uiState.value = UiState.Success(versionFeatures)
        }
    }

    private suspend fun getVersionFeaturesDeferred() = supervisorScope {
        val oreoFeaturesDeferred = async {
            api.getAndroidVersionFeatures(27)
        }
        val pieFeaturesDeferred = async {
            api.getAndroidVersionFeatures(28)
        }
        val android10FeaturesDeferred = async {
            api.getAndroidVersionFeatures(29)
        }
        listOf(oreoFeaturesDeferred, pieFeaturesDeferred, android10FeaturesDeferred)
    }
}