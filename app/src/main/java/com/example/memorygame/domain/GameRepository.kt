package com.example.memorygame.domain

import com.example.memorygame.data.db.GameDataBase
import com.example.memorygame.data.entity.Statistic
import com.example.memorygame.data.model.Card
import com.example.memorygame.data.model.Cards
import com.example.memorygame.data.retrofit.CardApiService
import com.example.memorygame.screens.CardData

class GameRepository(
    private val db: GameDataBase,
    private val apiService: CardApiService

) {
    suspend fun upset(item: Statistic) = db.getGameDao().upsert(item)
    suspend fun delete(item: Statistic) = db.getGameDao().delete(item)
    suspend fun getAllStatistics(): List<Statistic> {
        return db.getGameDao().getAllStatistics()
    }
    suspend fun getCardsForGame(numberOfCards: Int): List<CardData> {
        // Get deck
        val deckResponse = apiService.getDeck()
        if (!deckResponse.isSuccessful || deckResponse.body()?.success != true) {
            throw Exception("Failed to get deck: ${deckResponse.errorBody()?.string()}")
        }

        val deckId = deckResponse.body()?.deck_id ?: throw Exception("Deck ID is null")

        // Get cards (half the number since we'll duplicate them)
        val cardsResponse = apiService.getCards(deckId, numberOfCards / 2)
        if (!cardsResponse.isSuccessful || cardsResponse.body()?.success != true) {
            throw Exception("Failed to get cards: ${cardsResponse.errorBody()?.string()}")
        }

        val cards = cardsResponse.body()?.cards ?: throw Exception("Cards list is null")

        // Create pairs of cards
        val pairedCards = mutableListOf<CardData>()
        for (i in 0 until numberOfCards / 2) {
            val card = cards[i]
            pairedCards.add(CardData(i * 2, card.value, card.image))
            pairedCards.add(CardData(i * 2 + 1, card.value, card.image))
        }

        return pairedCards.shuffled()
    }
}