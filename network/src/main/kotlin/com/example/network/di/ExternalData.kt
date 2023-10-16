package com.example.network.di

external fun getReleasePokemonUrl(): String
object ExternalData {
    init {
        System.loadLibrary("native-lib")
    }
}

fun getPokemonUrl(): String = getReleasePokemonUrl()