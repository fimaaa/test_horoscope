package input

import com.baseapp.common.base.BaseBindingFragment
import com.example.sortdata.databinding.FragmentInputBinding

class InputFragment : BaseBindingFragment<FragmentInputBinding, InputViewModel>() {
    override fun onReadyAction() {
        binding.btnDone.setOnClickListener {
            viewModel.navigate(
                InputFragmentDirections.actionInputFragmentToShowFragment(
                    listOf(
                        binding.etNumber1.text.toString().toIntOrNull() ?: 0,
                        binding.etNumber2.text.toString().toIntOrNull() ?: 0,
                        binding.etNumber3.text.toString().toIntOrNull() ?: 0,
                        binding.etNumber4.text.toString().toIntOrNull() ?: 0,
                        binding.etNumber5.text.toString().toIntOrNull() ?: 0
                    ).toIntArray()
                )
            )
        }
    }
}