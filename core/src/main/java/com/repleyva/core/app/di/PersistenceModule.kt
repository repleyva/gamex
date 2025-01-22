package com.repleyva.core.app.di

import android.content.Context
import androidx.room.Room
import com.repleyva.core.data.local.dao.GameDao
import com.repleyva.core.data.local.room.GameDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideGameDatabase(@ApplicationContext context: Context): GameDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            GameDatabase::class.java,
            "game_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideGameDao(database: GameDatabase): GameDao {
        return database.gameDao()
    }

}