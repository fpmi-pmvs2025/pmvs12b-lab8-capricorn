package com.example.memorygame.data.retrofit

import com.example.memorygame.data.model.Cards
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CardApiService {
    @GET("new/shuffle/?deck_count=1")
    suspend fun getDeck(): Response<DeckResponse>

    @GET("{deckId}/draw/")
    suspend fun getCards(
        @Path("deckId") deckId: String,
        @Query("count") count: Int // Number of cards to draw
    ): Response<Cards>
}


data class DeckResponse(
    val success: Boolean,
    val deck_id: String,
    val remaining: Int,
    val shuffled: Boolean
)