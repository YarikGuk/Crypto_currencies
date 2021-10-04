package by.huk.crypto_currencies.ui.home

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.huk.crypto_currencies.MainViewModel
import by.huk.crypto_currencies.R
import by.huk.crypto_currencies.data.repository.CryptoRepository
import by.huk.crypto_currencies.databinding.FragmentHomeBinding
import by.huk.crypto_currencies.ui.utils.PRICE_CHANGE
import by.huk.crypto_currencies.ui.utils.SORT_BY_MARKET_CAP
import by.huk.crypto_currencies.ui.utils.USD
import org.koin.android.ext.android.inject


class HomeFragment : Fragment() {

    private val viewModel by inject<MainViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.anim.setBackgroundResource(R.drawable.custom_progressbar_anim)
        val anim = binding.anim.background as AnimatedVectorDrawable

        val adapter = CryptoAdapter(viewModel)
        binding.recyclerContainer.adapter = adapter




        viewModel.loadCryptoList(USD, SORT_BY_MARKET_CAP, 20, 2, true, PRICE_CHANGE)

        viewModel.initList.observe(requireActivity()) {
            Log.e("TAG", it.toString())
            adapter.initialize(it)

        }
        viewModel.isLoading.observe(requireActivity()) {
            binding.isLoading = it
            if (it == true) anim.start()
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}