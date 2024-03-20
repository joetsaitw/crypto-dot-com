package com.joe.cryptodotcom.presentation.model

data class SearchUiState(
    val searchQuery: String = "",
    val currencyType: CurrencyType = CurrencyType.All
)