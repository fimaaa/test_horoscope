package com.example.network.service

import com.example.model.pokemon.DetailPokemon
import com.example.model.pokemon.ListPokemon
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET("v2/pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0
    ): ListPokemon

    @GET("v2/pokemon/{pokemonId}")
    suspend fun getPokemonDetail(
        @Path("pokemonId") pokemonId: String
    ): DetailPokemon
}