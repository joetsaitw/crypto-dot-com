package com.joe.cryptodotcom.presentation.model

sealed class CurrencyListUiState {
    data object Initial : CurrencyListUiState()
    data object Loading : CurrencyListUiState()
    data object EmptyResult : CurrencyListUiState()
    data class Success(val currencies: List<CurrencyUiModel>) : CurrencyListUiState()
    data class Error(val message: String) : CurrencyListUiState()
}
