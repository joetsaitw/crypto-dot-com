package com.joe.cryptodotcom.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.joe.cryptodotcom.presentation.compose.CurrencyListScreen
import com.joe.cryptodotcom.presentation.theme.CryptoDotComTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DemoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoDotComTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CurrencyListScreen()
                }
            }
        }
    }
}