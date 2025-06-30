package com.example.bininfoapp.data.remote.dto

// Главный класс-обертка для ответа
data class BinResponseDto(
    val bank: BankDto?,
    val brand: String?,
    val country: CountryDto?,
    val number: NumberDto?,
    val prepaid: Boolean?,
    val scheme: String?,
    val type: String?
)

// Вложенный класс для информации о банке
data class BankDto(
    val city: String?,
    val name: String?,
    val phone: String?,
    val url: String?
)

// Вложенный класс для информации о стране
data class CountryDto(
    val alpha2: String?,
    val currency: String?,
    val emoji: String?,
    val latitude: Int?,
    val longitude: Int?,
    val name: String?,
    val numeric: String?
)

// Вложенный класс для информации о номере карты
data class NumberDto(
    val length: Int?,
    val luhn: Boolean?
)