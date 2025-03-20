package com.example.memorygame.data.retrofit

import com.example.memorygame.data.model.Cards
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CardApiService {
    @GET("https://deckofcardsapi.com/api/deck/new/")
    suspend fun getDeck()
    @GET("https://deckofcardsapi.com/api/deck/{deckId}/draw/?count=2")
    suspend fun getCards(
        @Path("deckId") deckId:String
    ): Call<Cards>
}