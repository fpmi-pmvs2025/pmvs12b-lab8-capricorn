package com.example.memorygame.di

import com.example.memorygame.data.retrofit.CardApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideCardApiService(): CardApiService {
        return Retrofit.Builder()
            .baseUrl("https://deckofcardsapi.com/api/deck/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CardApiService::class.java)
    }
}