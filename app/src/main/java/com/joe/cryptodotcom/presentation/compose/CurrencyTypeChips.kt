package com.joe.cryptodotcom.presentation.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joe.cryptodotcom.presentation.model.CurrencyType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyTypeChips(
    selectedChip: CurrencyType,
    modifier: Modifier = Modifier,
    onChipSelected: (CurrencyType) -> Unit
) {
    Row(modifier = modifier) {
        CurrencyType.entries.forEach {
            FilterChip(
                modifier = Modifier.padding(end = 8.dp),
                selected = selectedChip == it,
                onClick = {
                    onChipSelected(it)
                },
                label = { Text(it.name) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyTypeChipsPreview() {
    CurrencyTypeChips(
        selectedChip = CurrencyType.Fiat,
        onChipSelected = {},
    )
}