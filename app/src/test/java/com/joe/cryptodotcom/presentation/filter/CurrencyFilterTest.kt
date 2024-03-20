package com.joe.cryptodotcom.presentation.filter

import com.joe.cryptodotcom.presentation.model.CurrencyType
import com.joe.cryptodotcom.presentation.model.CurrencyUiModel
import com.joe.cryptodotcom.presentation.model.SearchUiState
import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyFilterTest {

    private val sut = CurrencyFilter()

    private val currencies = listOf(
        CurrencyUiModel("1", "Bitcoin", "BTC", "B", null),
        CurrencyUiModel("2", "Ethereum", "ETH", "E", null),
        CurrencyUiModel("3", "US Dollar", "USD", "U", "fiat_code_1"),
        CurrencyUiModel("4", "Euro", "EUR", "E", "fiat_code_2")
    )

    @Test
    fun `WHEN filter currencies with all types THEN return all currencies`() {
        val result = sut.filterCurrencies(
            currencies = currencies,
            searchUiState = SearchUiState("", CurrencyType.All)
        )
        assertEquals(currencies.size, result.size)
    }

    @Test
    fun `WHEN filter currencies with crypto type THEN return only cryptos`() {
        val result = sut.filterCurrencies(
            currencies = currencies,
            searchUiState = SearchUiState("", CurrencyType.Crypto)
        )
        assertEquals(2, result.size)
        assert(result.all { it.code == null })
    }

    @Test
    fun `WHEN filter currencies with fiat type THEN return only fiats`() {
        val result = sut.filterCurrencies(
            currencies = currencies,
            searchUiState = SearchUiState("", CurrencyType.Fiat)
        )
        assertEquals(2, result.size)
        assert(result.all { it.code != null })
    }

    @Test
    fun `WHEN filter currencies with empty query THEN return all currencies`() {
        val result = sut.filterCurrencies(
            currencies = currencies,
            searchUiState = SearchUiState("", CurrencyType.All)
        )
        assertEquals(currencies.size, result.size)
    }

    @Test
    fun `WHEN filter currencies with query matching name THEN return matched currencies`() {
        val result = sut.filterCurrencies(
            currencies = currencies,
            searchUiState = SearchUiState("Bitcoin", CurrencyType.All)
        )
        assertEquals(1, result.size)
        assertEquals("Bitcoin", result[0].name)
    }

    @Test
    fun `WHEN name partial match with space prefix THEN return matched currencies`() {
        val result = sut.filterCurrencies(
            currencies = currencies,
            searchUiState = SearchUiState("Dollar", CurrencyType.All)
        )
        assertEquals(1, result.size)
        assertEquals("US Dollar", result[0].name)
    }

    @Test
    fun `WHEN query starts with symbol THEN return matching currencies`() {
        val result = sut.filterCurrencies(
            currencies = currencies,
            searchUiState = SearchUiState("E", CurrencyType.All)
        )
        assertEquals(2, result.size)
        assertEquals("Ethereum", result[0].name)
        assertEquals("Euro", result[1].name)
    }


    @Test
    fun `WHEN filter currencies with query not matching any field THEN return empty list`() {
        val result = sut.filterCurrencies(
            currencies = currencies,
            searchUiState = SearchUiState("XYZ", CurrencyType.All)
        )
        assertEquals(0, result.size)
    }
}