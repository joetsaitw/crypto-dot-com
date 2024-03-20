package com.joe.cryptodotcom.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class CurrencyEntity(
    @PrimaryKey val id: String,
    val name: String,
    val symbol: String,
    val code: String? = null
)
