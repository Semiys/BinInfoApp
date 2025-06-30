package com.example.bininfoapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class HistoryItemEntity(
    val bin: String,
    val countryName: String?,
    val latitude: Int?,
    val longitude: Int?,
    val cardType: String?,
    val bankName: String?,
    val bankUrl: String?,
    val bankPhone: String?,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)