package com.repleyva.core.app.di

import com.repleyva.core.data.local.LocalDataSourceImpl
import com.repleyva.core.data.repository.GamesRepositoryImpl
import com.repleyva.core.domain.repository.GamesRepository
import com.repleyva.core.domain.source.LocalDataSource
import com.repleyva.core.domain.use_cases.GameInteractor
import com.repleyva.core.domain.use_cases.GameUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Binds
    @Singleton
    abstract fun bindRepository(gamesDataStore: GamesRepositoryImpl): GamesRepository

    @Binds
    @Singleton
    abstract fun bindGameUsecase(gameInteractor: GameInteractor): GameUseCase

}