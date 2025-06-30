package com.example.bininfoapp.domain.use_case

import com.example.bininfoapp.domain.model.BinInfo
import com.example.bininfoapp.domain.repository.BinRepository
import javax.inject.Inject

class GetHistoryItemUseCase @Inject constructor(
    private val repository: BinRepository
) {
    /**
     * Возвращает один элемент истории по его BIN.
     */
    suspend operator fun invoke(bin: String): BinInfo? {
        return repository.getHistoryItem(bin)
    }
}