package com.example.memorygame.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://deckofcardsapi.com/"

    val api: CardApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClientInstance.client)
            .build()

        retrofit.create(CardApiService::class.java)
    }
}
