package com.baseapp.repository.repository.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.baseapp.model.common.BaseResponse
import com.example.model.pokemon.ListPokemon
import com.example.network.service.PokemonService

class PokemonPagingSource(
    private val pokemonService: PokemonService,
    private val limit: Int,
    private val offset: Int
) : PagingSource<Int, ListPokemon.DataList>() {
    private val STARTING_PAGE_INDEX = 1
    override fun getRefreshKey(state: PagingState<Int, ListPokemon.DataList>): Int = STARTING_PAGE_INDEX

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListPokemon.DataList> = try {
        val page = params.key ?: STARTING_PAGE_INDEX

        val res = pokemonService.getPokemonList(limit, offset)
        BaseResponse(
            message = "success",
            data = res.result
        )

        LoadResult.Page(
            data = res.result,
            prevKey = if (page <= STARTING_PAGE_INDEX) null else page - 1,
            nextKey = if (res.result.isEmpty()) null else page + 1
        )
    } catch (e: Exception) {
        e.printStackTrace()
        LoadResult.Error(e)
    }
}