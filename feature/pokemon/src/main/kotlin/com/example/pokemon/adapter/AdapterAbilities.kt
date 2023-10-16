package com.example.Abilities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.model.pokemon.DetailPokemon
import com.example.pokemon.databinding.ItemPokemonBinding

class AdapterAbilities() : RecyclerView.Adapter<AdapterAbilities.ListAbilitiesViewHolder>() {
    private val listAbilities: ArrayList<DetailPokemon.Abilities> = arrayListOf()
    fun notifyItemDataSet(listAbilities: List<DetailPokemon.Abilities>) {
        this.listAbilities.clear()
        this.listAbilities.addAll(listAbilities)
        notifyDataSetChanged()
    }

    inner class ListAbilitiesViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            abilities: DetailPokemon.Abilities
        ) {
            binding.tvName.text = abilities.detailAbility.name
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListAbilitiesViewHolder = ListAbilitiesViewHolder(ItemPokemonBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ListAbilitiesViewHolder, position: Int) {
        holder.bind(listAbilities[position])
    }

    override fun getItemCount(): Int = listAbilities.size
}