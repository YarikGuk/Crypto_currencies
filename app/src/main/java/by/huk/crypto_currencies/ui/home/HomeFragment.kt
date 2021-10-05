package by.huk.crypto_currencies.ui.home

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.huk.crypto_currencies.MainViewModel
import by.huk.crypto_currencies.R
import by.huk.crypto_currencies.databinding.FragmentHomeBinding
import by.huk.crypto_currencies.ui.utils.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject
import java.util.zip.CheckedInputStream


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

        viewModel.loadCryptoListFromDB()



        viewModel.isLoading.observe(requireActivity()) {
            binding.isLoading = it
            if (it == true) anim.start()
        }

        viewModel.initList.observe(requireActivity()) {
          adapter.initialize(it)
        }
        viewModel.page.observe(requireActivity()) {
          Log.e("TAG",it.toString())
        }


        var checkedItem = 0
        val singleItems = arrayOf(getString(R.string.by_market_cap),getString(R.string.by_price), getString(R.string.by_volume))
        binding.mainToolbar.menu.findItem(R.id.item_sort).setOnMenuItemClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.sort))
                .setNegativeButton(resources.getString(R.string.cancel)) {dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                    adapter.refreshList(checkedItem)
                    viewModel.refreshPageCount()
                    when(checkedItem){
                        0 -> viewModel.loadCryptoList(SORT_BY_MARKET_CAP,1)
                        1 -> viewModel.loadCryptoList(SORT_BY_PRICE,1)
                        2 -> viewModel.loadCryptoList(SORT_BY_VOLUME,1)

                    }
                }
                .setSingleChoiceItems(singleItems, checkedItem) { _, which ->
                    checkedItem = which
                }
                .show()
            true
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}