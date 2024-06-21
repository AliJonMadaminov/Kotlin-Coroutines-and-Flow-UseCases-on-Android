package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase6

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.FakeSuccessApiStub
import com.lukaslechner.coroutineusecasesonandroid.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule

import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RetryNetworkRequestViewModelTest {

    private val receivedUiStates = mutableListOf<UiState>()

    @get:Rule
    val replaceMainDispatcherRule = ReplaceMainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `performNetworkRequest() should return success when api returns success`() = runTest {
        // Arrange
        val fakeApi = FakeSuccessApiStub()
        val viewModel = RetryNetworkRequestViewModel(fakeApi)
        observeViewModel(viewModel)

        // Act
        assertTrue(receivedUiStates.isEmpty())
        viewModel.performNetworkRequest()
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
    fun `performSingleNetworkRequest() should retry network request two times`() = runTest {
        // Arrange
        val fakeApi = FakeSuccessOnThirdTryApiStub()
        val viewModel = RetryNetworkRequestViewModel(fakeApi)
        observeViewModel(viewModel)

        // Act
        assertTrue(receivedUiStates.isEmpty())
        viewModel.performNetworkRequest()
        advanceUntilIdle()

        // Assert
        assertEquals(3, fakeApi.requestCount)
        assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(mockAndroidVersions)
            ),
            receivedUiStates
        )
    }
    @Test
    fun `performSingleNetworkRequest() should return Error UiState on 3 unsuccessful network responses`() = runTest {
        // Arrange
        val fakeApi = FakeVersionsErrorApi()
        val viewModel = RetryNetworkRequestViewModel(fakeApi)
        observeViewModel(viewModel)

        // Act
        assertTrue(receivedUiStates.isEmpty())
        viewModel.performNetworkRequest()
        advanceUntilIdle()

        // Assert
        assertEquals(3, fakeApi.requestCount)
        assertTrue(receivedUiStates.lastOrNull() is UiState.Error)
    }

    private fun observeViewModel(viewModel: RetryNetworkRequestViewModel) {
        viewModel.uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }
}