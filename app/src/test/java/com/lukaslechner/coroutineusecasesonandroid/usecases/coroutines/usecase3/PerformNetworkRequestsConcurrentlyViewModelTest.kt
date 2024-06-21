package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase3

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesAndroid10
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesOreo
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesPie
import com.lukaslechner.coroutineusecasesonandroid.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PerformNetworkRequestsConcurrentlyViewModelTest {

    @get:Rule
    val replaceMainDispatcherRule = ReplaceMainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val receivedUiStates = mutableListOf<UiState>()


    @Test
    fun performNetworkRequestsSequentially() = runTest {
        val delayMillis = 1000L
        // Arrange
        val fakeApi = FakeSuccessApi(delayMillis)
        val viewModel = PerformNetworkRequestsConcurrentlyViewModel(fakeApi)
        observeViewModel(viewModel)

        // Act
        assert(receivedUiStates.isEmpty())
        viewModel.performNetworkRequestsSequentially()
        advanceUntilIdle()

        // Assert
        assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(
                    listOf(
                        mockVersionFeaturesOreo,
                        mockVersionFeaturesPie,
                        mockVersionFeaturesAndroid10
                    )
                )
            ),
            receivedUiStates
        )
        assertEquals(3000L, currentTime)
    }

    @Test
    fun performNetworkRequestsConcurrently() = runTest {
        val delayMillis = 1000L
        // Arrange
        val fakeApi = FakeSuccessApi(delayMillis)
        val viewModel = PerformNetworkRequestsConcurrentlyViewModel(fakeApi)
        observeViewModel(viewModel)

        // Act
        assert(receivedUiStates.isEmpty())
        viewModel.performNetworkRequestsConcurrently()
        advanceUntilIdle()

        // Assert
        assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(
                    listOf(
                        mockVersionFeaturesOreo,
                        mockVersionFeaturesPie,
                        mockVersionFeaturesAndroid10
                    )
                )
            ),
            receivedUiStates
        )
        assertEquals(1000L, currentTime)
    }

    private fun observeViewModel(viewModel: PerformNetworkRequestsConcurrentlyViewModel) {
        viewModel.uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }
}