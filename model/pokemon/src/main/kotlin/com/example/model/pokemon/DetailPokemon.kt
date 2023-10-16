package com.example.model.pokemon

import com.squareup.moshi.Json

data class DetailPokemon(
    @Json(name = "name") var name: String = "",
    @Json(name = "id") var id: String = "",
    @Json(name = "abilities") var listAbility: List<Abilities> = listOf(),
    @Json(name = "moves") var listMove: List<Moves> = listOf(),
    @Json(name = "sprites") var listImage: Map<String, Any?> = mapOf()
) {
    data class Moves(
        @Json(name = "name") var name: String = "",
        @Json(name = "url") var imageUrl: String = ""
    )

    data class Abilities(
        @Json(name = "is_hidden") var isHidden: Boolean = true,
        @Json(name = "slot") var slot: Int = 0,
        @Json(name = "ability") var detailAbility: Ability = Ability()
    ) {
        data class Ability(
            @Json(name = "name") var name: String = "",
            @Json(name = "url") var imageUrl: String = ""
        )
    }
}