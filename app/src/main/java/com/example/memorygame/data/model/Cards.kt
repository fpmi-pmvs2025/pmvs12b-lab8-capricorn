package com.example.memorygame.data.model


data class Cards(
    val success: Boolean,
    val deck_id: String,
    val remaining: Int,
    val cards: List<Card>
)