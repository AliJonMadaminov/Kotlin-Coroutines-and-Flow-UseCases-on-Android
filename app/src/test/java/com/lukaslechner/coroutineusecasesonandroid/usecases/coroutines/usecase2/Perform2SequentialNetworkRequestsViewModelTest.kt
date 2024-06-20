package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesAndroid10
import com.lukaslechner.coroutineusecasesonandroid.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test

class Perform2SequentialNetworkRequestsViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val replaceMainDispatcherRule = ReplaceMainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val receivedUiStates = mutableListOf<UiState>()

    @Test
    fun `should return success with latest version features if both requests succeed`() {
        // Arrange
        val fakeApi = FakeSuccessApi()
        val viewModel = Perform2SequentialNetworkRequestsViewModel(fakeApi)
        observeViewModel(viewModel)

        // Act
        viewModel.perform2SequentialNetworkRequest()

        // Assert
        assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(mockVersionFeaturesAndroid10)
            ), receivedUiStates
        )
    }

    @Test
    fun `should return error if first request fails`() {
        val fakeApi = FakeAndroidVersionsErrorApi()
        val viewModel = Perform2SequentialNetworkRequestsViewModel(fakeApi)
        observeViewModel(viewModel)

        viewModel.perform2SequentialNetworkRequest()

        assertEquals(
            listOf(
                UiState.Loading,
                UiState.Error("Something went wrong!")
            ), receivedUiStates
        )
    }
    @Test
    fun `should return error if second request fails`() {
        val fakeApi = FakeVersionFeaturesErrorApi()
        val viewModel = Perform2SequentialNetworkRequestsViewModel(fakeApi)
        observeViewModel(viewModel)

        viewModel.perform2SequentialNetworkRequest()

        assertEquals(
            listOf(
                UiState.Loading,
                UiState.Error("Something went wrong!")
            ), receivedUiStates
        )
    }

    private fun observeViewModel(viewModel: Perform2SequentialNetworkRequestsViewModel) {
        viewModel.uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }
}