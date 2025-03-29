package com.example.memorygame.data.model

data class DeckResponse(
    val success: Boolean,
    val deck_id: String,
    val remaining: Int,
    val shuffled: Boolean
)
