package com.example.pokemon.detail

import com.baseapp.common.base.BaseViewModel
import com.baseapp.common.extension.safeApiCollect
import com.baseapp.model.common.ViewState
import com.baseapp.repository.repository.PokemonRepository
import com.example.model.pokemon.DetailPokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DetailPokemonViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : BaseViewModel() {

    private val _detailPokemon = MutableStateFlow<ViewState<DetailPokemon>>(ViewState.LOADING)
    val detailPokemon: StateFlow<ViewState<DetailPokemon>>
        get() = _detailPokemon

    fun getPokemonDetail(pokemonID: String) {
        safeApiCollect(_detailPokemon, {
            pokemonRepository.getPokemonDetail(pokemonID)
        })
    }
}