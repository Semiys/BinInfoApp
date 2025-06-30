package com.example.bininfoapp.presentation.history_screen

import com.example.bininfoapp.domain.model.BinInfo

data class HistoryState(
    val historyItems: List<BinInfo> = emptyList()
)