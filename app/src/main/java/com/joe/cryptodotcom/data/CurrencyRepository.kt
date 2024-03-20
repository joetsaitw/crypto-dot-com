package com.joe.cryptodotcom.data

import com.joe.cryptodotcom.data.model.CurrencyEntity
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    fun getCurrencies(): Flow<List<CurrencyEntity>>
    suspend fun refreshCurrencies()
    suspend fun clearCurrencies()
}
