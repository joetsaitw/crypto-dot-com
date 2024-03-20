package com.joe.cryptodotcom.data

import com.joe.cryptodotcom.R
import com.joe.cryptodotcom.data.db.CurrencyDao
import com.joe.cryptodotcom.data.model.CurrencyEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val jsonLoader: JsonLoader,
) : CurrencyRepository {

    override fun getCurrencies(): Flow<List<CurrencyEntity>> {
        return currencyDao.getAll()
    }

    override suspend fun refreshCurrencies() {
        // Delay 1 second to simulate IO operation
        delay(1000)
        supervisorScope {
            val cryptoCurrenciesDeferred = async {
                jsonLoader.loadCurrencyListFromRaw(R.raw.currency_list_crypto)
            }
            val fiatCurrenciesDeferred = async {
                jsonLoader.loadCurrencyListFromRaw(R.raw.currency_list_fiat)
            }

            val cryptoCurrencies = try {
                cryptoCurrenciesDeferred.await()
            } catch (e: Throwable) {
                emptyList()
            }
            val fiatCurrencies = try {
                fiatCurrenciesDeferred.await()
            } catch (e: Throwable) {
                emptyList()
            }
            currencyDao.insertAll(cryptoCurrencies + fiatCurrencies)
        }
    }

    override suspend fun clearCurrencies() {
        // Delay 1 second to simulate IO operation
        delay(1000)
        currencyDao.clearAll()
    }
}

