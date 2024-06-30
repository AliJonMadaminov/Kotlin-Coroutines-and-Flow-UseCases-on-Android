package com.lukaslechner.coroutineusecasesonandroid.usecases.flow.usecase1

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class FlowUseCase1ViewModel(
    stockPriceDataSource: StockPriceDataSource
) : BaseViewModel<UiState>() {
    
    val currentStockPriceAsLiveData: LiveData<UiState> = stockPriceDataSource.latestStockList
        .map { UiState.Success(it) as UiState }
        .onStart { emit(UiState.Loading) }
        .catch { cause: Throwable ->
            emit(UiState.Error("Something went wrong"))
        }
        .asLiveData()

}