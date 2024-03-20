package com.joe.cryptodotcom.presentation.mapper

import com.joe.cryptodotcom.data.model.CurrencyEntity
import com.joe.cryptodotcom.presentation.model.CurrencyUiModel
import javax.inject.Inject

class CurrencyUiModelMapper @Inject constructor() {

    fun map(entity: CurrencyEntity): CurrencyUiModel {
        return CurrencyUiModel(
            id = entity.id,
            name = entity.name,
            symbol = entity.symbol,
            displaySymbol = entity.symbol.take(1),
            code = entity.code
        )
    }
}