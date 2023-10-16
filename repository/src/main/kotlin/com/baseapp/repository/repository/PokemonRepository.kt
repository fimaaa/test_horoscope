package com.baseapp.repository.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.baseapp.common.BaseIODispatcher
import com.baseapp.common.extension.safeGetResponse
import com.baseapp.local.dao.PokemonDao
import com.baseapp.model.common.BaseResponse
import com.baseapp.model.common.ViewState
import com.baseapp.repository.common.Repository
import com.baseapp.repository.repository.datasource.PokemonPagingSource
import com.example.model.pokemon.DetailPokemon
import com.example.model.pokemon.ListPokemon
import com.example.network.service.PokemonService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface PokemonRepository : Repository {
    fun getPokemonDataSet(
        limit: Int = 50,
        offset: Int = 0
    ): LiveData<PagingData<ListPokemon.DataList>>

    @WorkerThread
    suspend fun getListPokemon(
        limit: Int = 50,
        offset: Int = 0
    ): Flow<ViewState<List<ListPokemon.DataList>>>

    @WorkerThread
    suspend fun getPokemonDetail(
        pokemonId: String
    ): Flow<ViewState<DetailPokemon>>
}

@Singleton
class PokemonRepositoryImpl @Inject constructor(
//    private val orderService: OrderService,
    private val pokemonService: PokemonService,
    private val pokemonDao: PokemonDao,
    @BaseIODispatcher private val ioDispatcher: CoroutineDispatcher
) : PokemonRepository {
    override fun getPokemonDataSet(
        limit: Int,
        offset: Int
    ): LiveData<PagingData<ListPokemon.DataList>> = Pager(
        config = PagingConfig(
            pageSize = 30,
            maxSize = 100,
            enablePlaceholders = false,
            prefetchDistance = 10
        ),
        pagingSourceFactory = {
            PokemonPagingSource(
                pokemonService = pokemonService,
                limit = limit,
                offset = offset
            )
        }
    ).liveData

    override suspend fun getListPokemon(
        limit: Int,
        offset: Int
    ): Flow<ViewState<List<ListPokemon.DataList>>> = safeGetResponse(ioDispatcher) {
        val listPokemon = mutableListOf<ListPokemon.DataList>()
        val resLocal = pokemonDao.getListPokemon()
        listPokemon.addAll(resLocal)
        if (resLocal.isEmpty()) {
            listPokemon.clear()
            val res = pokemonService.getPokemonList(limit, offset)
            pokemonDao.insert(res.result)
            listPokemon.addAll(res.result)
        }

        BaseResponse(
            message = "success",
            data = listPokemon
        )
    }

    override suspend fun getPokemonDetail(
        pokemonId: String
    ): Flow<ViewState<DetailPokemon>> = safeGetResponse(ioDispatcher) {
        val res = pokemonService.getPokemonDetail(pokemonId)
        BaseResponse(
            message = "success",
            data = res
        )
    }
}