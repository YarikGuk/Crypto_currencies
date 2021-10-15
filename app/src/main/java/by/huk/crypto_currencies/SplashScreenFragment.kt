package by.huk.crypto_currencies


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.huk.crypto_currencies.databinding.FragmentSpalshScreenBinding
import by.huk.crypto_currencies.ui.utils.SORT_BY_MARKET_CAP
import by.huk.crypto_currencies.ui.utils.applyLoopingAnimatedVectorDrawable
import by.huk.crypto_currencies.ui.utils.launchWhenStarted
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class SplashScreenFragment : Fragment() {
    private lateinit var binding: FragmentSpalshScreenBinding
    private val viewModel by inject<SplashViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSpalshScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.animSplashScreen.applyLoopingAnimatedVectorDrawable(R.drawable.custom_progressbar_anim)

        viewModel.loadCryptoList(SORT_BY_MARKET_CAP, 1)

        viewModel.initList.onEach {
            if (it.isNotEmpty()) {
                viewModel.insertInitListToDB(it)
                parentFragmentManager.popBackStack()
            }
        }.launchWhenStarted(lifecycleScope)
    }
}