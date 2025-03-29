package com.example.memorygame.domain

import com.example.memorygame.data.db.GameDataBase
import com.example.memorygame.data.entity.Statistic
import com.example.memorygame.data.model.Card
import com.example.memorygame.data.model.Cards
import com.example.memorygame.data.retrofit.CardApiService

class GameRepository(
    private val db: GameDataBase,
    private val apiService: CardApiService

) {
    suspend fun upset(item: Statistic) = db.getGameDao().upsert(item)
    suspend fun delete(item: Statistic) = db.getGameDao().delete(item)
    suspend fun getAllStatistics(): List<Statistic> {
        return db.getGameDao().getAllStatistics()
    }
    suspend fun getDeck(): String {
        val response = apiService.getDeck()
        if (!response.isSuccessful || response.body()?.success != true) {
            throw Exception("Failed to get deck: ${response.errorBody()?.string()}")
        }
        return response.body()?.deck_id ?: throw Exception("Deck ID is null")
    }

    suspend fun getCardsForGame(deckId: String): List<Card> {
        val response = apiService.getCards(deckId, 10)
        if (!response.isSuccessful || response.body()?.success != true) {
            throw Exception("Failed to get cards: ${response.errorBody()?.string()}")
        }

        val cards = response.body()?.cards ?: throw Exception("Cards list is null")
        return (cards + cards).mapIndexed { index, card ->
            card.copy(code = index.toString())
        }.shuffled()
    }
}