package com.lukaslechner.coroutineusecasesonandroid.usecases.flow.usecase1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class FlowUseCase1ViewModel(
    stockPriceDataSource: StockPriceDataSource
) : BaseViewModel<UiState>() {

    private val _currentStockPriceAsLiveData = MutableLiveData<UiState>()
    val currentStockPriceAsLiveData: LiveData<UiState> = _currentStockPriceAsLiveData

    init {
        viewModelScope.launch {
            stockPriceDataSource.latestStockList.collect { stockList ->
                _currentStockPriceAsLiveData.value = UiState.Success(stockList)
            }
        }
    }

}