package com.example.bininfoapp.domain.use_case

import com.example.bininfoapp.domain.model.BinInfo
import com.example.bininfoapp.domain.repository.BinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val repository: BinRepository
) {
    /**
     * Возвращает поток с историей запросов.
     */
    operator fun invoke(): Flow<List<BinInfo>> {
        return repository.getSearchHistory()
    }
}