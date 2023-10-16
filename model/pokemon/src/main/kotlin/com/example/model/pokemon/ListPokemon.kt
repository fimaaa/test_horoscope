package com.example.model.pokemon

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class ListPokemon(
    @Json(name = "results") var result: List<DataList> = listOf()
) {
    @Entity(tableName = "pokemon")
    data class DataList(
        @PrimaryKey
        @SerializedName("name")var name: String = "",
        @SerializedName("url") var url: String = ""
    )
}
