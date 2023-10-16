package com.example.pokemon.splashscreen

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import com.baseapp.common.base.BaseBindingFragment
import com.example.pokemon.databinding.FragmentPokemonSplashscreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment :
    BaseBindingFragment<FragmentPokemonSplashscreenBinding, SplashscreenViewModel>() {
    override fun onReadyAction() {
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.navigate(
                SplashScreenFragmentDirections.actionSplashScreenFragmentToListPokemonFragment()
            )
        }, 3000L)
    }
}