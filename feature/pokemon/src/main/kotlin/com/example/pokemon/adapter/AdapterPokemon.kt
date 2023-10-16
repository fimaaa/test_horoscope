package com.example.pokemon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.model.pokemon.ListPokemon
import com.example.pokemon.databinding.ItemPokemonBinding

class AdapterPokemon(
    private val callBack: (ListPokemon.DataList) -> Unit
) : RecyclerView.Adapter<AdapterPokemon.ListPokemonViewHolder>() {
    private val listPokemonFull: ArrayList<ListPokemon.DataList> = arrayListOf()
    private val listPokemon: ArrayList<ListPokemon.DataList> = arrayListOf()
    fun notifyItemDataSet(listPokemon: List<ListPokemon.DataList>) {
        this.listPokemonFull.clear()
        this.listPokemonFull.addAll(listPokemon)
        this.listPokemon.clear()
        this.listPokemon.addAll(listPokemon)
        notifyDataSetChanged()
    }

    fun filterItemDataSet(name: String) {
        this.listPokemon.clear()
        val tempList = listPokemonFull
        this.listPokemon.addAll(tempList.filter {
            it.name.contains(name, true)
        })
        notifyDataSetChanged()
    }
    inner class ListPokemonViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            pokemon: ListPokemon.DataList
        ) {
            binding.root.setOnClickListener {
                callBack.invoke(pokemon)
            }
            binding.tvName.text = pokemon.name
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListPokemonViewHolder = ListPokemonViewHolder(ItemPokemonBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ListPokemonViewHolder, position: Int) {
        holder.bind(listPokemon[position])
    }

    override fun getItemCount(): Int = listPokemon.size
}