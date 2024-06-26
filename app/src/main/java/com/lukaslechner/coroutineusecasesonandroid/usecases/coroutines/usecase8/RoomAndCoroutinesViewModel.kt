package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase8

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomAndCoroutinesViewModel(
    private val api: MockApi,
    private val database: AndroidVersionDao
) : BaseViewModel<UiState>() {

    fun loadData() {
        uiState.value = UiState.Loading.LoadFromDb
        viewModelScope.launch {
            val localVersions = database.getAndroidVersions()
            if (localVersions.isEmpty()) {
                uiState.value = UiState.Error(DataSource.DATABASE, "No versions found")
            } else {
                uiState.value = UiState.Success(DataSource.DATABASE, localVersions.mapToUiModelList())
            }
            uiState.value = UiState.Loading.LoadFromNetwork
            try {
                val remoteVersions = api.getRecentAndroidVersions()
                remoteVersions.forEach {
                    database.insert(it.mapToEntity())
                }
                uiState.value = UiState.Success(DataSource.NETWORK, remoteVersions)
            } catch (e: Exception) {
                uiState.value = UiState.Error(DataSource.NETWORK, "Something went wrong")
            }
        }

    }

    fun clearDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            database.clear()
        }
    }
}

enum class DataSource(val dataSourceName: String) {
    DATABASE("Database"),
    NETWORK("Network")
}