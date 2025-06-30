package com.example.bininfoapp.presentation.main_screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bininfoapp.domain.model.BinInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToHistory: () -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    // Получаем контекст для создания Intent'ов
    val context = LocalContext.current

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
                BinInfoCard(
                    info = info,
                    onCoordinatesClick = { lat, lon ->
                        openMap(context, lat, lon)
                    },
                    onUrlClick = { url ->
                        openUrl(context, url)
                    },
                    onPhoneClick = { phone ->
                        openPhone(context, phone)
                    }
                )
            }

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
fun BinInfoCard(
    info: BinInfo,
    onCoordinatesClick: (Int, Int) -> Unit,
    onUrlClick: (String) -> Unit,
    onPhoneClick: (String) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            InfoRow("Country:", info.countryName ?: "N/A")
            InfoRow(
                label = "Coordinates:",
                value = formatCoordinates(info.countryCoordinates),
                onClick = {
                    info.countryCoordinates?.first?.let { lat ->
                        info.countryCoordinates.second?.let { lon ->
                            onCoordinatesClick(lat, lon)
                        }
                    }
                }
            )
            InfoRow("Card Type:", info.cardType ?: "N/A")
            InfoRow("Bank:", info.bankName ?: "N/A")
            InfoRow("Bank URL:", info.bankUrl, onClick = { info.bankUrl?.let(onUrlClick) })
            InfoRow("Bank Phone:", info.bankPhone, onClick = { info.bankPhone?.let(onPhoneClick) })
        }
    }
}

@Composable
fun InfoRow(label: String, value: String?, onClick: (() -> Unit)? = null) {
    if (value != null) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )

            // Оборачиваем текст в Box и делаем Box кликабельным
            Box(
                modifier = Modifier
                    .weight(1f)
                    .then(
                        if (onClick != null) Modifier.clickable { onClick() } else Modifier
                    )
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

private fun openMap(context: Context, lat: Int, lon: Int) {
    val gmmIntentUri = Uri.parse("geo:$lat,$lon")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps") // Указываем, чтобы открылось именно в Google Картах
    if (mapIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(mapIntent)
    }
}

private fun openUrl(context: Context, url: String) {
    // Добавляем https://, если его нет
    val fullUrl = if (!url.startsWith("http://") && !url.startsWith("https://")) {
        "https://$url"
    } else {
        url
    }
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(fullUrl))
    context.startActivity(intent)
}

private fun openPhone(context: Context, phone: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
    context.startActivity(intent)
}

fun formatCoordinates(coords: Pair<Int?, Int?>?): String {
    return if (coords?.first != null && coords.second != null) {
        "lat: ${coords.first}, lon: ${coords.second}"
    } else {
        "N/A"
    }
}