package com.joe.cryptodotcom.presentation.filter

import com.joe.cryptodotcom.presentation.model.CurrencyType
import com.joe.cryptodotcom.presentation.model.CurrencyUiModel
import com.joe.cryptodotcom.presentation.model.SearchUiState
import javax.inject.Inject

class CurrencyFilter @Inject constructor() {

    fun filterCurrencies(
        currencies: List<CurrencyUiModel>,
        searchUiState: SearchUiState
    ): List<CurrencyUiModel> {
        val (query, type) = searchUiState

        return currencies
            .filterByType(type)
            .filterByQuery(query)
    }

    private fun List<CurrencyUiModel>.filterByType(type: CurrencyType): List<CurrencyUiModel> {
        return when (type) {
            CurrencyType.All -> this
            CurrencyType.Crypto -> filter { it.code == null }
            CurrencyType.Fiat -> filter { it.code != null }
        }
    }

    private fun List<CurrencyUiModel>.filterByQuery(query: String): List<CurrencyUiModel> {
        if (query.isBlank()) return this
        return filter {
            it.name.startsWith(query, ignoreCase = true) ||
                    it.name.contains(" $query", ignoreCase = true) ||
                    it.symbol.startsWith(query, ignoreCase = true)
        }
    }
}