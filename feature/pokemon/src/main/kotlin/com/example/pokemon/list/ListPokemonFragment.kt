package com.example.pokemon.list

import androidx.core.widget.addTextChangedListener
import com.baseapp.common.base.BaseBindingFragment
import com.baseapp.common.extension.safeCollect
import com.baseapp.model.common.ViewState
import com.example.pokemon.adapter.AdapterPokemon
import com.example.pokemon.databinding.FragmentPokemonListBinding

class ListPokemonFragment :
    BaseBindingFragment<FragmentPokemonListBinding, ListPokemonViewModel>() {
    private lateinit var adapter: AdapterPokemon
    override fun onInitialization(binding: FragmentPokemonListBinding) {
        super.onInitialization(binding)
        adapter = AdapterPokemon {
            viewModel.navigate(
                ListPokemonFragmentDirections.actionListPokemonFragmentToDetailPokemonFragment(it.name)
            )
        }
        binding.rcvPokemon.adapter = adapter
        viewModel.getListPokemon()
    }

    override fun onObserveAction() {
        super.onObserveAction()
        safeCollect(viewModel.listPokemon) {
            when (it) {
                is ViewState.SUCCESS -> {
                    adapter.notifyItemDataSet(it.data.sortedBy { data -> data.name })
                }

                is ViewState.LOADING -> {
                }

                is ViewState.ERROR -> {
                }
            }
        }
    }

    override fun onReadyAction() {
        binding.etNumber1.addTextChangedListener {
            adapter.filterItemDataSet(it.toString())
        }
    }
}