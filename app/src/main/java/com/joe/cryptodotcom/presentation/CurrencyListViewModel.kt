package com.joe.cryptodotcom.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joe.cryptodotcom.data.CurrencyRepository
import com.joe.cryptodotcom.presentation.filter.CurrencyFilter
import com.joe.cryptodotcom.presentation.mapper.CurrencyUiModelMapper
import com.joe.cryptodotcom.presentation.model.CurrencyListUiState
import com.joe.cryptodotcom.presentation.model.CurrencyType
import com.joe.cryptodotcom.presentation.model.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    private val repository: CurrencyRepository,
    private val currencyFilter: CurrencyFilter,
    private val currencyUiModelMapper: CurrencyUiModelMapper,
) : ViewModel() {

    private val _searchUiState = MutableStateFlow(SearchUiState())
    val searchUiState: StateFlow<SearchUiState> = _searchUiState.asStateFlow()

    private val _uiState = MutableStateFlow<CurrencyListUiState>(CurrencyListUiState.Initial)
    val uiState: StateFlow<CurrencyListUiState> = _uiState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        _uiState.value = CurrencyListUiState.Error(e.message ?: UNKNOWN_ERROR)
    }

    init {
        subscribeCurrencyUpdates()
    }

    fun loadData() {
        if (_uiState.value is CurrencyListUiState.Error) {
            subscribeCurrencyUpdates()
        }

        viewModelScope.launch(exceptionHandler) {
            _uiState.value = CurrencyListUiState.Loading
            repository.refreshCurrencies()
        }
    }

    fun clearData() {
        if (_uiState.value !is CurrencyListUiState.Success) return

        viewModelScope.launch(exceptionHandler) {
            _uiState.value = CurrencyListUiState.Loading
            repository.clearCurrencies()
        }
    }

    fun performSearch(query: String) {
        _searchUiState.update { it.copy(searchQuery = query) }
    }

    fun updateCurrencyType(type: CurrencyType) {
        _searchUiState.update { it.copy(currencyType = type) }
    }

    private fun subscribeCurrencyUpdates() {
        val currenciesFlow = repository.getCurrencies().map { currencies ->
            currencies.map(currencyUiModelMapper::map)
        }

        combine(
            currenciesFlow,
            searchUiState
        ) { currencies, searchState ->
            if (currencies.isEmpty()) return@combine CurrencyListUiState.Initial

            val filteredList = currencyFilter.filterCurrencies(currencies, searchState)
            if (filteredList.isEmpty()) {
                CurrencyListUiState.EmptyResult
            } else {
                CurrencyListUiState.Success(filteredList)
            }
        }
            .onEach { _uiState.value = it }
            .catch {
                _uiState.value = CurrencyListUiState.Error("Failed to load currencies")
            }
            .launchIn(viewModelScope)
    }

    companion object {
        private const val UNKNOWN_ERROR = "Unknown error"
    }
}