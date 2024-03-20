package com.joe.cryptodotcom.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.joe.cryptodotcom.data.model.CurrencyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM CurrencyEntity")
    fun getAll(): Flow<List<CurrencyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencies: List<CurrencyEntity>)

    @Query("DELETE FROM CurrencyEntity")
    suspend fun clearAll()
}
