package com.baseapp.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.baseapp.local.common.dao.BaseDao
import com.example.model.pokemon.ListPokemon

@Dao
interface PokemonDao : BaseDao<ListPokemon.DataList> {
    @Query("SELECT * FROM pokemon")
    suspend fun getListPokemon(): List<ListPokemon.DataList>
}