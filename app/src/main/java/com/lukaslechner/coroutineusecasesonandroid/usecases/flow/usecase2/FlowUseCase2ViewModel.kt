package com.lukaslechner.coroutineusecasesonandroid.usecases.flow.usecase2

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.withIndex
import timber.log.Timber

class FlowUseCase2ViewModel(
    stockPriceDataSource: StockPriceDataSource,
    defaultDispatcher: CoroutineDispatcher
) : BaseViewModel<UiState>() {


    /*

    Flow exercise 1 Goals
        (1) only update stock list when Alphabet(Google) (stock.name ="Alphabet (Google)") stock price is > 2300$
        (2) only show stocks of "United States" (stock.country == "United States")
        (3) show the correct rank (stock.rank) within "United States", not the world wide rank
        (4) filter out Apple  (stock.name ="Apple") and Microsoft (stock.name ="Microsoft"), so that Google is number one
        (5) only show company if it is one of the biggest 10 companies of the "United States" (stock.rank <= 10)
        (6) stop flow collection after 10 emissions from the dataSource
        (7) log out the number of the current emission so that we can check if flow collection stops after exactly 10 emissions
        (8) Perform all flow processing on a background thread

     */

    val currentStockPriceAsLiveData: LiveData<UiState> =
        stockPriceDataSource.latestStockList
            .withIndex()
            .onEach { indexedValue ->
                Timber.d("Processing emission ${indexedValue.index + 1}")
            }
            .map { it.value }
            .take(10)
            .filter { stockList ->
                val googlePrice = stockList.find {
                    stock -> stock.name.contains("Alphabet", true)
                }?.currentPrice ?: return@filter false
                googlePrice > 2300f
            }
            .cancellable()
            .map { stockList ->
                stockList
                    .filter { it.country == "United States" }
                    .filter { it.name != "Apple" && it.name != "Microsoft" }
                    .mapIndexed { index, stock ->
                        stock.copy(rank = index + 1)
                    }
                    .filter { stock -> stock.rank <= 10 }
            }
            .map {
                UiState.Success(it) as UiState
            }
            .onStart {
                emit(UiState.Loading)
            }
            .asLiveData(defaultDispatcher)
}
