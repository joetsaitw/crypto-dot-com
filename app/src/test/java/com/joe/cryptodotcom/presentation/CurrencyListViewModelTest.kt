package com.joe.cryptodotcom.presentation

import app.cash.turbine.test
import com.joe.cryptodotcom.FakeCurrencyRepository
import com.joe.cryptodotcom.MainDispatcherRule
import com.joe.cryptodotcom.presentation.filter.CurrencyFilter
import com.joe.cryptodotcom.presentation.mapper.CurrencyUiModelMapper
import com.joe.cryptodotcom.presentation.model.CurrencyListUiState
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CurrencyListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var sut: CurrencyListViewModel

    @Before
    fun setup() {
        sut = CurrencyListViewModel(
            repository = FakeCurrencyRepository(),
            currencyFilter = CurrencyFilter(),
            currencyUiModelMapper = CurrencyUiModelMapper()
        )
    }

    @Test
    fun `WHEN loadData is initially called THEN uiState is set to Loading and then Success`() =
        runTest {
            sut.uiState.test {
                assertEquals(CurrencyListUiState.Initial, awaitItem())

                sut.loadData()
                assertEquals(CurrencyListUiState.Loading, awaitItem())

                val successState = awaitItem() as CurrencyListUiState.Success
                assertEquals(4, successState.currencies.size)
            }
        }

    @Test
    fun `WHEN currencies are refreshed THEN uiState updates to Success with refreshed data`() =
        runTest {
            sut.uiState.test {
                // Initial load
                sut.loadData()
                awaitItem() // Initial
                awaitItem() // Loading
                awaitItem() // Success

                // Simulate refresh
                sut.loadData()

                assertEquals(CurrencyListUiState.Loading, awaitItem())
                val successState = awaitItem() as CurrencyListUiState.Success
                assertEquals(
                    4,
                    successState.currencies.size
                ) // Assuming refresh doesn't change the number of items
            }
        }

    @Test
    fun `WHEN clearData is called THEN uiState is set to Initial`() = runTest {
        sut.uiState.test {
            // Initial load
            sut.loadData()
            awaitItem() // Initial
            awaitItem() // Loading
            awaitItem() // Success

            sut.clearData()

            assertEquals(CurrencyListUiState.Loading, awaitItem())
            assertEquals(CurrencyListUiState.Initial, awaitItem())
        }
    }


    @Test
    fun `WHEN performSearch is called with a valid query THEN uiState is updated with filtered currencies`() =
        runTest {
            sut.uiState.test {
                // Load initial data
                sut.loadData()
                awaitItem() // Initial
                awaitItem() // Loading
                awaitItem() // Success

                // Perform search
                sut.performSearch("Bitcoin")

                // Expecting a Success state with filtered list containing only Bitcoin
                val successState = awaitItem() as CurrencyListUiState.Success
                assertTrue(successState.currencies.any { it.name == "Bitcoin" })
                assertEquals(1, successState.currencies.size)
            }
        }

    @Test
    fun `WHEN performSearch is called with a query that matches no currencies THEN uiState is set to EmptyResult`() =
        runTest {
            sut.uiState.test {
                // Load initial data
                sut.loadData()
                awaitItem() // Initial
                awaitItem() // Loading
                awaitItem() // Success

                // Search for a non-existent currency
                sut.performSearch("NonExistentCurrency")

                // Expecting EmptyResult state
                assertEquals(CurrencyListUiState.EmptyResult, awaitItem())
            }
        }
}