package com.example.bininfoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [HistoryItemEntity::class],
    version = 1
)
abstract class BinDatabase : RoomDatabase() {

    abstract val dao: HistoryDao

    companion object {
        const val DATABASE_NAME = "bin_db"
    }
}