package com.example.memorygame.domain

import com.example.memorygame.data.db.GameDataBase
import com.example.memorygame.data.entity.Statistic
import com.example.memorygame.data.model.Cards
import com.example.memorygame.data.retrofit.CardApiService

class GameRepository(
    private val db: GameDataBase,
    private val apiService: CardApiService

) {
    suspend fun upset(item: Statistic) = db.getGameDao().upsert(item)
    suspend fun delete(item: Statistic) = db.getGameDao().delete(item)
    fun getAllStatistics() = db.getGameDao().getAllStatistics()
    suspend fun getDeck() = apiService.getDeck()
    suspend fun getCards(deckId: String): Cards? {
        val response = apiService.getCards(deckId).execute()
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}