package com.example.pokemon.list

import com.baseapp.common.base.BaseViewModel
import com.baseapp.common.extension.safeApiCollect
import com.baseapp.model.common.ViewState
import com.baseapp.repository.repository.PokemonRepository
import com.example.model.pokemon.ListPokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ListPokemonViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : BaseViewModel() {

    private val _listPokemon: MutableStateFlow<ViewState<List<ListPokemon.DataList>>> =
        MutableStateFlow(ViewState.EMPTY())
    val listPokemon: StateFlow<ViewState<List<ListPokemon.DataList>>>
        get() = _listPokemon

    fun getListPokemon() {
        safeApiCollect(_listPokemon, {
            pokemonRepository.getListPokemon()
        })
    }
}