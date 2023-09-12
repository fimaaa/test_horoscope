package com.baseapp.local.di

import android.content.Context
import androidx.room.Room
import com.baseapp.local.AppDatabase
import com.baseapp.local.dao.HoroscopeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalModule {
    @Singleton
    @Provides
    fun provideFavMovieDatabase(
        @ApplicationContext app: Context
    ): AppDatabase = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "horoscope_db"
    ).build()

    @Singleton
    @Provides
    fun provideHoroscopeDao(db: AppDatabase): HoroscopeDao = db.getHoroscopeDao()
}