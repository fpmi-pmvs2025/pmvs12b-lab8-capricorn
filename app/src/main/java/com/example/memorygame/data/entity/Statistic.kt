package com.example.memorygame.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "statistics")
data class Statistic(
    @ColumnInfo(name = "duration")
    var duration: Long, // Длительность игры в миллисекундах

    @ColumnInfo(name = "start_time")
    var startTime: Date, // Время начала игры

    @ColumnInfo(name = "number_of_cards")
    var numberOfCards: Int, // Количество карт

    @ColumnInfo(name = "attempts")
    var attempts: Int // Количество попыток
) {
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null
}