package com.example.memorygame.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "statistics")
data class Statistic(
    // Duration of the game in milliseconds
    @ColumnInfo(name = "duration")
    var duration: Long,

    // Game start time
    @ColumnInfo(name = "start_time")
    var startTime: Date,

    // Number of cards
    @ColumnInfo(name = "number_of_cards")
    var numberOfCards: Int,

    // Number of attempts
    @ColumnInfo(name = "attempts")
    var attempts: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null
}