package com.example.bininfoapp.presentation.main_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bininfoapp.domain.model.BinInfo

// Я добавил аннотацию для использования экспериментальных API Material 3
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToHistory: () -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    // SnackbarHostState нужно "запомнить"
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("BIN Checker") },
                actions = {
                    IconButton(onClick = onNavigateToHistory) {
                        Icon(Icons.Default.History, contentDescription = "History")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = state.binInput,
                onValueChange = viewModel::onBinInput,
                label = { Text("Enter first 6-8 digits of card") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = viewModel::onSearch,
                enabled = state.binInput.length >= 6 && !state.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Search")
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (state.isLoading) {
                CircularProgressIndicator()
            }

            state.binInfo?.let { info ->
                BinInfoCard(info = info)
            }

            // LaunchedEffect нужен для вызова suspend-функций в Composable
            LaunchedEffect(key1 = state.error) {
                if (state.error != null) {
                    snackbarHostState.showSnackbar(
                        message = state.error
                    )
                }
            }
        }
    }
}

@Composable
fun BinInfoCard(info: BinInfo) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            InfoRow("Country:", info.countryName ?: "N/A")
            InfoRow("Coordinates:", formatCoordinates(info.countryCoordinates))
            InfoRow("Card Type:", info.cardType ?: "N/A")
            InfoRow("Bank:", info.bankName ?: "N/A")
            InfoRow("Bank URL:", info.bankUrl)
            // Здесь была опечатка Info-row, я её исправил
            InfoRow("Bank Phone:", info.bankPhone)
        }
    }
}

@Composable
fun InfoRow(label: String, value: String?) {
    // Убрал isClickable, т.к. мы пока не реализуем эту логику
    if (value != null) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
            // TODO: Add click handling for URL and Phone
            Text(text = value, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
        }
    }
}

fun formatCoordinates(coords: Pair<Int?, Int?>?): String {
    return if (coords?.first != null && coords.second != null) {
        "lat: ${coords.first}, lon: ${coords.second}"
    } else {
        "N/A"
    }
}