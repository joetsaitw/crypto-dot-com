package com.joe.cryptodotcom

import com.joe.cryptodotcom.data.CurrencyRepository
import com.joe.cryptodotcom.data.model.CurrencyEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeCurrencyRepository : CurrencyRepository {

    private val fakeCurrencyEntityList = listOf(
        CurrencyEntity(
            id = "BTC",
            name = "Bitcoin",
            symbol = "BTC",
            code = null
        ),
        CurrencyEntity(
            id = "ETH",
            name = "Ethereum",
            symbol = "ETH",
            code = null
        ),
        CurrencyEntity(
            id = "USD",
            name = "US Dollar",
            symbol = "USD",
            code = "USD"
        ),
        CurrencyEntity(
            id = "EUR",
            name = "Euro",
            symbol = "EUR",
            code = "EUR"
        )
    )

    private val currenciesFlow = MutableSharedFlow<List<CurrencyEntity>>()

    override fun getCurrencies(): Flow<List<CurrencyEntity>> {
        return currenciesFlow
    }

    override suspend fun refreshCurrencies() {
        currenciesFlow.emit(fakeCurrencyEntityList)
    }

    override suspend fun clearCurrencies() {
        currenciesFlow.emit(emptyList())
    }
}