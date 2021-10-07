package by.huk.crypto_currencies.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.huk.crypto_currencies.MainViewModel
import by.huk.crypto_currencies.databinding.FragmentDetailsBinding
import by.huk.crypto_currencies.databinding.FragmentHomeBinding
import org.koin.android.ext.android.inject

class DetailsFragment: Fragment() {

    private val viewModel by inject<MainViewModel>()
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}