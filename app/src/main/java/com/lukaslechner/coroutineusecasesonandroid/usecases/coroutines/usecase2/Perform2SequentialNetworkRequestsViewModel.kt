package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Perform2SequentialNetworkRequestsViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun perform2SequentialNetworkRequest() {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            try {
                val recentVersion = mockApi.getRecentAndroidVersions().last()
                val versionFeatures = mockApi.getAndroidVersionFeatures(recentVersion.apiLevel)
                uiState.value = UiState.Success(versionFeatures)
            } catch (t: Throwable) {
                uiState.value = UiState.Error("Something went wrong!")
            }
        }
    }
}