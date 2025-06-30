package com.example.bininfoapp.presentation.main_screen

import com.example.bininfoapp.domain.model.BinInfo

data class MainScreenState(
    val binInput: String = "",
    val isLoading: Boolean = false,
    val binInfo: BinInfo? = null,
    val error: String? = null
)