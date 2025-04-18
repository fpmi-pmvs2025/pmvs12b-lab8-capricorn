package com.example.memorygame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.memorygame.data.entity.Statistic

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item : Statistic)
    @Query("SELECT * FROM statistics")
    suspend fun getAllStatistics(): List<Statistic>
}
