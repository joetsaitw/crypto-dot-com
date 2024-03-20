package com.joe.cryptodotcom.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joe.cryptodotcom.data.model.CurrencyEntity

@Database(entities = [CurrencyEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}
