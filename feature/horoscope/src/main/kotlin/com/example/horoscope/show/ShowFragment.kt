package com.example.horoscope.show

import androidx.navigation.fragment.navArgs
import com.baseapp.common.base.BaseBindingFragment
import com.baseapp.common.extension.safeCollect
import com.example.horoscope.databinding.FragmentHoroscopeShowBinding

class ShowFragment : BaseBindingFragment<FragmentHoroscopeShowBinding, ShowViewModel>() {
    private val args: ShowFragmentArgs by navArgs()

    override fun onInitialization(binding: FragmentHoroscopeShowBinding) {
        super.onInitialization(binding)
        binding.tvName.text = "Hello, ${args.nama}"
        viewModel.init(args.date)
    }

    override fun onObserveAction() {
        super.onObserveAction()
        safeCollect(viewModel.textAge) {
            binding.tvAge.text = it.asString(requireContext())
        }
        safeCollect(viewModel.textZodiac) {
            binding.tvHoroscope.text = it.asString(requireContext())
        }
    }

    override fun onReadyAction() {
    }
}