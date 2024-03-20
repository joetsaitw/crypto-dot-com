package com.joe.cryptodotcom.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.joe.cryptodotcom.presentation.model.CurrencyListUiState
import com.joe.cryptodotcom.presentation.CurrencyListViewModel
import com.joe.cryptodotcom.presentation.model.CurrencyType

@Composable
fun CurrencyListScreen(viewModel: CurrencyListViewModel = viewModel()) {
    val searchState by viewModel.searchUiState.collectAsState()
    val currencyListUiState by viewModel.uiState.collectAsState()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(currencyListUiState) {
        lazyListState.animateScrollToItem(0)
    }

    Scaffold(
        topBar = {
            TopBar(
                searchQuery = searchState.searchQuery,
                selectedChip = searchState.currencyType,
                onTextChange = viewModel::performSearch,
                onChipSelected = viewModel::updateCurrencyType,
            )
        },
        bottomBar = {
            BottomBar(
                onClearData = viewModel::clearData,
                onLoadData = viewModel::loadData,
            )
        },
        content = {
            CurrencyListContent(currencyListUiState, it, lazyListState)
        }
    )
}

@Composable
fun CurrencyListContent(
    state: CurrencyListUiState,
    paddingValues: PaddingValues,
    lazyListState: LazyListState
) {
    Box(modifier = Modifier.padding(paddingValues)) {
        when (state) {
            CurrencyListUiState.Initial -> {
                Instruction(
                    imageVector = Icons.Rounded.Refresh,
                    text = "Click load button to load the data."
                )
            }

            CurrencyListUiState.Loading -> {
                LoadingIndicator()
            }

            CurrencyListUiState.EmptyResult -> {
                Instruction(
                    imageVector = Icons.Rounded.Search,
                    text = "No results. Please try another query!"
                )
            }

            is CurrencyListUiState.Success -> {
                CurrencyList(state.currencies, lazyListState)
            }

            is CurrencyListUiState.Error -> {
                Instruction(
                    imageVector = Icons.Rounded.Warning,
                    text = state.message
                )
            }
        }
    }
}

@Composable
fun TopBar(
    searchQuery: String,
    selectedChip: CurrencyType,
    onTextChange: (String) -> Unit,
    onChipSelected: (CurrencyType) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        SearchBar(
            text = searchQuery,
            onTextChange = onTextChange,
        )
        CurrencyTypeChips(
            selectedChip = selectedChip,
            modifier = Modifier.padding(top = 8.dp),
            onChipSelected = onChipSelected
        )
    }
}

@Composable
fun BottomBar(
    onClearData: () -> Unit,
    onLoadData: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = onClearData,
            modifier = Modifier.weight(1f),
            content = { Text("Clear") }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = onLoadData,
            modifier = Modifier.weight(1f),
            content = { Text("Load") }
        )
    }
}

