package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase5

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkRequestWithTimeoutViewModelTest {

    private val receivedUiStates = mutableListOf<UiState>()

    @get:Rule
    val replaceMainDispatcherRule = ReplaceMainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `performNetworkRequest() should return Success UiState on successful network request within timeout`() = runTest {
        val requestDelay = 1000L
        val timeout = 1001L
        // Arrange
        val fakeApi = FakeSuccessApi(requestDelay)
        val viewModel = NetworkRequestWithTimeoutViewModel(fakeApi)
        observeViewModel(viewModel)

        // Act
        assertTrue(receivedUiStates.isEmpty())
        viewModel.performNetworkRequest(timeout = timeout)
        advanceUntilIdle()

        // Assert
        assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(mockAndroidVersions)
            ),
            receivedUiStates
        )
    }
    @Test
    fun `performNetworkRequest() should return Error UiState with timeout error message if timeout gets exceeded`() = runTest {
        val requestDelay = 1000L
        val timeout = 200L
        // Arrange
        val fakeApi = FakeSuccessApi(requestDelay)
        val viewModel = NetworkRequestWithTimeoutViewModel(fakeApi)
        observeViewModel(viewModel)

        // Act
        assertTrue(receivedUiStates.isEmpty())
        viewModel.performNetworkRequest(timeout = timeout)
        advanceUntilIdle()

        // Assert
        assertTrue(receivedUiStates.lastOrNull() is UiState.Error)
    }

    private fun observeViewModel(viewModel: NetworkRequestWithTimeoutViewModel) {
        viewModel.uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }

}