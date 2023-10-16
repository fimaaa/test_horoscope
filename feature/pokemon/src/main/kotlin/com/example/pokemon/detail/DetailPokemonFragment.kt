package com.example.pokemon.detail

import androidx.navigation.fragment.navArgs
import com.baseapp.common.base.BaseBindingFragment
import com.baseapp.common.extension.safeCollect
import com.baseapp.model.common.ViewState
import com.bumptech.glide.Glide
import com.example.Abilities.adapter.AdapterAbilities
import com.example.pokemon.databinding.FragmentPokemonDetailBinding

class DetailPokemonFragment :
    BaseBindingFragment<FragmentPokemonDetailBinding, DetailPokemonViewModel>() {
    private val args: DetailPokemonFragmentArgs by navArgs()
    private lateinit var adapter: AdapterAbilities

    override fun onInitialization(binding: FragmentPokemonDetailBinding) {
        super.onInitialization(binding)
        adapter = AdapterAbilities()
        binding.rcvAbilities.adapter = adapter
        viewModel.getPokemonDetail(args.pokemonId)
    }

    override fun onObserveAction() {
        super.onObserveAction()
        safeCollect(viewModel.detailPokemon) {
            when (it) {
                is ViewState.SUCCESS -> {
                    binding.tvName.text = it.data.name
                    Glide.with(requireContext())
                        .load(it.data.listImage["front_default"])
                        .into(binding.ivImage)
                    adapter.notifyItemDataSet(it.data.listAbility)
                }
            }
        }
    }

    override fun onReadyAction() {
    }
}