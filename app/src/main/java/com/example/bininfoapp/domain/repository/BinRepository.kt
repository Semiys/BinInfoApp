package com.example.bininfoapp.domain.repository

import com.example.bininfoapp.domain.model.BinInfo
import com.example.bininfoapp.util.Resource
import kotlinx.coroutines.flow.Flow


interface BinRepository {
    /**
     * Получение информации о карте по её BIN номеру.
     * @param bin номер карты для поиска.
     * @return Resource<BinInfo> обертка с результатом.
     */
    suspend fun getBinInfo(bin: String): Resource<BinInfo>

    /**
     * Получение всей истории поисковых запросов.
     * @return Flow со списком всех записей.
     */
    fun getSearchHistory(): Flow<List<BinInfo>>

    /**
     * Получение одной записи из истории по её BIN номеру.
     * @param bin номер для поиска в истории.
     * @return BinInfo? - найденная запись или null.
     */
    suspend fun getHistoryItem(bin: String): BinInfo?
}