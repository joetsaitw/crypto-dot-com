package com.joe.cryptodotcom.di

import android.content.Context
import androidx.room.Room
import com.joe.cryptodotcom.data.db.AppDatabase
import com.joe.cryptodotcom.data.db.CurrencyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "currency_database"
        ).build()
    }

    @Provides
    fun provideCurrencyDao(database: AppDatabase): CurrencyDao {
        return database.currencyDao()
    }
}
