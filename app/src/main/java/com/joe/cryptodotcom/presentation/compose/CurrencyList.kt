package com.joe.cryptodotcom.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joe.cryptodotcom.presentation.model.CurrencyUiModel

@Composable
fun CurrencyList(
    currencies: List<CurrencyUiModel>,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier, state = listState) {
        items(
            items = currencies,
            key = { it.id }
        ) { currency ->
            CurrencyItem(currency)
            Divider()
        }
    }
}

@Composable
private fun CurrencyItem(currency: CurrencyUiModel) {
    Row(
        modifier = Modifier
            .clickable { /* Handle item click */ }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SymbolIcon(symbol = currency.displaySymbol)
        Text(
            text = currency.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        )
        Text(
            text = currency.id,
            style = MaterialTheme.typography.bodyMedium
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Go to details"
        )
    }
}

@Composable
fun SymbolIcon(symbol: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
    ) {
        Text(text = symbol, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyItemPreview() {
    CurrencyItem(CurrencyUiModel("BTC", "Bitcoin", "BTC", "Q", "code"))
}