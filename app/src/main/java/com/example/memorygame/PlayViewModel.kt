package com.example.memorygame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorygame.data.entity.Statistic
import com.example.memorygame.data.retrofit.CardApiService
import com.example.memorygame.domain.GameRepository
import com.example.memorygame.screens.CardData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlayViewModel() : ViewModel() {
    private val _cards = MutableStateFlow<List<CardData>>(emptyList())
    val cards: StateFlow<List<CardData>> = _cards

    /*private val _gameStatsList = MutableStateFlow<List<Statistic>>(emptyList())
    val gameStatsList: StateFlow<List<Statistic>> = _gameStatsList

    init {
        fetchGameStats()
    }

    private fun fetchGameStats() {
        viewModelScope.launch {
            val stats = repository.getAllStatistics()
            _gameStatsList.value = stats
        }
    }*/

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://deckofcardsapi.com/api/deck/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(CardApiService::class.java)

    fun fetchCards(numberOfCards: Int) {
        viewModelScope.launch {
            val deckResponse = apiService.getDeck()
            if (deckResponse.isSuccessful && deckResponse.body()?.success == true) {
                val deckId = deckResponse.body()?.deck_id
                val cardsResponse = apiService.getCards(deckId!!, numberOfCards)
                if (cardsResponse.isSuccessful && cardsResponse.body()?.success == true) {
                    val cards = cardsResponse.body()?.cards ?: emptyList()
                    val pairedCards = mutableListOf<CardData>()

                    // Create pairs of cards
                    for (i in 0 until numberOfCards / 2) {
                        val card = cards[i]
                        pairedCards.add(CardData(i * 2, card.value, card.image))
                        pairedCards.add(CardData(i * 2 + 1, card.value, card.image))
                    }

                    // Shuffle the cards
                    pairedCards.shuffle()
                    _cards.value = pairedCards
                }
            }
        }
    }
}
