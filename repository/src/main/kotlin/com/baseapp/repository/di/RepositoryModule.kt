package com.baseapp.repository.di

import com.baseapp.repository.repository.HoroscopeRepository
import com.baseapp.repository.repository.HoroscopeRepositoryImpl
import com.baseapp.repository.repository.PokemonRepository
import com.baseapp.repository.repository.PokemonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class, ViewModelComponent::class)
@Module
interface RepositoryModule {
    @Binds
    fun bindsHoroscopeRepository(
        horoscopeRepositoryImpl: HoroscopeRepositoryImpl
    ): HoroscopeRepository

    @Binds
    fun bindsPokemonRepository(
        pokemonRepositoryImpl: PokemonRepositoryImpl
    ): PokemonRepository
}