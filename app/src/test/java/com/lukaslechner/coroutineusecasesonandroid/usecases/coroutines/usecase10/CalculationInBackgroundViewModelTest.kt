package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase10

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CalculationInBackgroundViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val replaceMainDispatcherRule = ReplaceMainDispatcherRule()

    private val receivedUiStates = mutableListOf<UiState>()

    @Test
    fun performCalculation() = runTest {
        // Arrange
        val viewModel = CalculationInBackgroundViewModel(defaultDispatcher = StandardTestDispatcher())
        val factorialOf = 3
        observeViewModel(viewModel)

        // Act
        Assert.assertTrue(receivedUiStates.isEmpty())
        viewModel.performCalculation(factorialOf)
        runCurrent()

        // Assert
        Assert.assertEquals(UiState.Loading, receivedUiStates[0])
        Assert.assertEquals("6", (receivedUiStates[1] as UiState.Success).result)
    }

    private fun observeViewModel(viewModel: CalculationInBackgroundViewModel) {
        viewModel.uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }
}