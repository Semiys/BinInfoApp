package com.example.bininfoapp.domain.model

data class BinInfo(
    val bin: String,
    val countryName: String?,
    val countryCoordinates:Pair<Int?, Int?>?,
    val cardType:String?,
    val bankName: String?,
    val bankUrl:String?,
    val bankPhone: String?
)
