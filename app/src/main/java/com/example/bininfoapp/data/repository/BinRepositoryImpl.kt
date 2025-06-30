package com.example.bininfoapp.data.repository

import com.example.bininfoapp.data.local.BinDatabase
import com.example.bininfoapp.data.remote.BinlistApi
import com.example.bininfoapp.data.toBinInfo
import com.example.bininfoapp.data.toHistoryItemEntity
import com.example.bininfoapp.domain.model.BinInfo
import com.example.bininfoapp.domain.repository.BinRepository
import com.example.bininfoapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BinRepositoryImpl @Inject constructor(
    private val api: BinlistApi,
    db: BinDatabase
) : BinRepository {

    private val dao = db.dao

    override suspend fun getBinInfo(bin: String): Resource<BinInfo> {
        return try {
            val response = api.getInfoByBin(bin)
            val binInfo = response.toBinInfo(bin)

            // Сохраняем успешный результат в базу данных
            dao.insertHistoryItem(binInfo.toHistoryItemEntity())

            Resource.Success(binInfo)
        } catch (e: HttpException) {
            // Ошибка, связанная с HTTP (например, 404 Not Found)
            Resource.Error(message = "Could not load data: ${e.message()}")
        } catch (e: IOException) {
            // Ошибка, связанная с сетью (нет интернета и т.д.)
            Resource.Error(message = "Network error: Please check your connection.")
        }
    }

    override fun getSearchHistory(): Flow<List<BinInfo>> {
        return dao.getHistory().map { entities ->
            entities.map { it.toBinInfo() }
        }
    }

    override suspend fun getHistoryItem(bin: String): BinInfo? {
        return dao.getHistoryItemByBin(bin)?.toBinInfo()
    }
}