package com.example.memorygame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorygame.data.entity.Statistic
import com.example.memorygame.domain.GameRepository
import com.example.memorygame.screens.CardData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val repository: GameRepository
) : ViewModel() {
    private val _cards = MutableStateFlow<List<CardData>>(emptyList())
    val cards: StateFlow<List<CardData>> = _cards.asStateFlow()

    private val _gameStatsList = MutableStateFlow<List<Statistic>>(emptyList())
    val gameStatsList: StateFlow<List<Statistic>> = _gameStatsList.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        fetchGameStats()
    }

    private fun fetchGameStats() {
        viewModelScope.launch {
            try {
                _gameStatsList.value = repository.getAllStatistics()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load game statistics: ${e.message}"
            }
        }
    }

    fun fetchCards(numberOfCards: Int) {
        viewModelScope.launch {
            try {
                _cards.value = repository.getCardsForGame(numberOfCards)
                _errorMessage.value = null // Clear previous errors if successful
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load cards: ${e.message}"
                _cards.value = emptyList()
            }
        }
    }

    fun saveGameResult(statistic: Statistic) {
        viewModelScope.launch {
            try {
                repository.upset(statistic)
                fetchGameStats() // Refresh stats after saving
            } catch (e: Exception) {
                _errorMessage.value = "Failed to save game result: ${e.message}"
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}