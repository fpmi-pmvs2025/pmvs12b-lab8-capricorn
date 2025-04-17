package com.example.memorygame.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.memorygame.data.entity.Statistic

@Database(
    entities = [Statistic::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class GameDataBase: RoomDatabase() {
    abstract fun getGameDao(): GameDao

    companion object {
        @Volatile
        private var instance: GameDataBase? = null
        private val LOCK = Any()
        operator  fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also{ instance = it }
        }
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                GameDataBase::class.java, "GameDb.db").build()
    }
}