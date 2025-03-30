package com.example.memorygame.di

import com.example.memorygame.data.db.GameDataBase
import com.example.memorygame.data.retrofit.CardApiService
import com.example.memorygame.domain.GameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideGameRepository(
        db: GameDataBase,
        apiService: CardApiService
    ): GameRepository {
        return GameRepository(db, apiService)
    }
}