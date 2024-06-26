package com.lukaslechner.coroutineusecasesonandroid.usecases.flow.usecase1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

class FlowUseCase1ViewModel(
    stockPriceDataSource: StockPriceDataSource
) : BaseViewModel<UiState>() {

    private val _currentStockPriceAsLiveData = MutableLiveData<UiState>()
    val currentStockPriceAsLiveData: LiveData<UiState> = _currentStockPriceAsLiveData

    init {
        stockPriceDataSource.latestStockList
            .onEach { stockList ->
                _currentStockPriceAsLiveData.value = UiState.Success(stockList)
            }
            .launchIn(viewModelScope)
    }
}