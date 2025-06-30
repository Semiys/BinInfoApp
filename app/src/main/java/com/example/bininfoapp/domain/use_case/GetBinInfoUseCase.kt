package com.example.bininfoapp.domain.use_case

import com.example.bininfoapp.domain.model.BinInfo
import com.example.bininfoapp.domain.repository.BinRepository
import com.example.bininfoapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBinInfoUseCase @Inject constructor(
    private val repository: BinRepository
) {
    /**
     * Запускает получение информации о карте.
     * @param bin Номер карты для поиска.
     * @return Flow, который эмитит состояния загрузки, успеха или ошибки.
     */
    operator fun invoke(bin: String): Flow<Resource<BinInfo>> = flow {
        if (bin.length < 6) {
            // Не делаем запрос, если BIN слишком короткий
            return@flow
        }
        try {
            emit(Resource.Loading())
            val binInfo = repository.getBinInfo(bin)
            emit(binInfo)
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred: ${e.localizedMessage}"))
        }
    }
}