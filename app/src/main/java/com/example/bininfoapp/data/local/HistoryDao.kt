package com.example.bininfoapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    /**
     * Вставляет новую запись в историю.
     * Если запись с таким BIN уже существует, она будет заменена.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoryItem(item: HistoryItemEntity)

    /**
     * Получает всю историю запросов в виде потока данных.
     * Сортировка по убыванию ID, чтобы новые записи были сверху.
     */
    @Query("SELECT * FROM history_table ORDER BY id DESC")
    fun getHistory(): Flow<List<HistoryItemEntity>>

    /**
     * Получает одну запись из истории по её BIN.
     */
    @Query("SELECT * FROM history_table WHERE bin = :bin")
    suspend fun getHistoryItemByBin(bin: String): HistoryItemEntity?
}