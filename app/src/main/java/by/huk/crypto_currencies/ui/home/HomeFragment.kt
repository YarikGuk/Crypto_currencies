package by.huk.crypto_currencies.ui.home

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.huk.crypto_currencies.R
import by.huk.crypto_currencies.databinding.FragmentHomeBinding
import by.huk.crypto_currencies.ui.utils.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject


class HomeFragment : Fragment() {
    private val viewModel by inject<HomeViewModel>()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.anim.setBackgroundResource(R.drawable.custom_progressbar_anim)
        val anim = binding.anim.background as AnimatedVectorDrawable

        val adapter = CryptoAdapter(viewModel)
        binding.recyclerContainer.adapter = adapter
        waitForTransition(binding.recyclerContainer)

        viewModel.isLoading.onEach {
            binding.isLoading = it
            if (it) anim.start()
        }.launchWhenStarted(lifecycleScope)

        viewModel.initList.onEach {
            if (it is HomeViewModel.ListWrapper.NewList)
                if (it.list.isNotEmpty()) adapter.initialize(it.list)
        }.launchWhenStarted(lifecycleScope)

        fun refreshList() {
            adapter.refreshList(viewModel.checkedItem)
            viewModel.refreshPageCount()
            when (viewModel.checkedItem) {
                0 -> viewModel.loadCryptoList(SORT_BY_MARKET_CAP, 1)
                1 -> viewModel.loadCryptoList(SORT_BY_PRICE, 1)
                2 -> viewModel.loadCryptoList(SORT_BY_VOLUME, 1)
            }
        }

        val singleItems = arrayOf(getString(R.string.by_market_cap),
            getString(R.string.by_price),
            getString(R.string.by_volume))
        binding.mainToolbar.menu.findItem(R.id.item_sort).setOnMenuItemClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.sort))
                .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                    refreshList()
                }
                .setSingleChoiceItems(singleItems, viewModel.checkedItem) { _, which ->
                    viewModel.checkedItem = which
                }
                .show()
            true
        }
        binding.swipeToRefresh.setOnRefreshListener {
            refreshList()
            binding.swipeToRefresh.isRefreshing = false
        }
    }
}