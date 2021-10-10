package by.huk.crypto_currencies


import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import by.huk.crypto_currencies.databinding.ActivitySpalshScreenBinding
import by.huk.crypto_currencies.ui.utils.PRICE_CHANGE
import by.huk.crypto_currencies.ui.utils.SORT_BY_MARKET_CAP
import by.huk.crypto_currencies.ui.utils.USD
import by.huk.crypto_currencies.ui.utils.applyLoopingAnimatedVectorDrawable
import org.koin.android.ext.android.inject

class SpalshScreen : AppCompatActivity() {


    private lateinit var binding: ActivitySpalshScreenBinding
    private val viewModel by inject<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpalshScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
        binding.animSplashScreen.applyLoopingAnimatedVectorDrawable(R.drawable.custom_progressbar_anim)

        viewModel.loadCryptoList(SORT_BY_MARKET_CAP, 1)


        viewModel.initList.observe(this) {
            viewModel.insertInitListToDB(it)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


    }

}