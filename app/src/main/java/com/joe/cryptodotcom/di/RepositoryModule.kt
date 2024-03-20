package com.joe.cryptodotcom.di

import com.joe.cryptodotcom.data.CurrencyRepository
import com.joe.cryptodotcom.data.CurrencyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCurrencyRepository(
        currencyRepositoryImpl: CurrencyRepositoryImpl
    ): CurrencyRepository
}
