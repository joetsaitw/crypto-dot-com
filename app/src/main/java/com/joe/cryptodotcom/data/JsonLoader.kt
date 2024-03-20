package com.joe.cryptodotcom.data

import android.content.Context
import com.joe.cryptodotcom.data.model.CurrencyEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.InputStreamReader
import javax.inject.Inject

class JsonLoader @Inject constructor(@ApplicationContext private val context: Context) {

    suspend fun loadCurrencyListFromRaw(
        resourceId: Int
    ): List<CurrencyEntity> = withContext(Dispatchers.IO) {
        val rawResourceStream = context.resources.openRawResource(resourceId)
        val jsonString = InputStreamReader(rawResourceStream).use { it.readText() }
        Json.decodeFromString(jsonString)
    }
}
