package com.baseapp.repository.di

import com.baseapp.repository.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class, ViewModelComponent::class)
@Module
interface RepositoryModule {
    @Binds
    fun bindsSessionsRepository(
        horoscopeRepositoryImpl: HoroscopeRepositoryImpl
    ): HoroscopeRepository
}