package show

import androidx.navigation.fragment.navArgs
import com.baseapp.common.base.BaseBindingFragment
import com.example.sortdata.databinding.FragmentShowBinding

class ShowFragment : BaseBindingFragment<FragmentShowBinding, ShowViewModel>() {
    private val args by navArgs<ShowFragmentArgs>()
    override fun onReadyAction() {
        args.listNumber.toMutableList().sort()
        binding.tvDataMax.text = args.listNumber.toMutableList().max().toString()
        binding.tvDataSort.text = args.listNumber.toMutableList().toString()
    }
}