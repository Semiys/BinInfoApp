package com.example.bininfoapp.data

import com.example.bininfoapp.data.local.HistoryItemEntity
import com.example.bininfoapp.data.remote.dto.BinResponseDto
import com.example.bininfoapp.domain.model.BinInfo

/**
 * Преобразует DTO из сети в нашу доменную модель BinInfo.
 */
fun BinResponseDto.toBinInfo(bin: String): BinInfo {
    return BinInfo(
        bin = bin,
        countryName = country?.name,
        countryCoordinates = Pair(country?.latitude, country?.longitude),
        cardType = type,
        bankName = bank?.name,
        bankUrl = bank?.url,
        bankPhone = bank?.phone
    )
}

/**
 * Преобразует доменную модель BinInfo в сущность для сохранения в БД.
 */
fun BinInfo.toHistoryItemEntity(): HistoryItemEntity {
    return HistoryItemEntity(
        bin = bin,
        countryName = countryName,
        latitude = countryCoordinates?.first,
        longitude = countryCoordinates?.second,
        cardType = cardType,
        bankName = bankName,
        bankUrl = bankUrl,
        bankPhone = bankPhone
    )
}

/**
 * Преобразует сущность из БД в нашу доменную модель BinInfo.
 */
fun HistoryItemEntity.toBinInfo(): BinInfo {
    return BinInfo(
        bin = bin,
        countryName = countryName,
        countryCoordinates = Pair(latitude, longitude),
        cardType = cardType,
        bankName = bankName,
        bankUrl = bankUrl,
        bankPhone = bankPhone
    )
}