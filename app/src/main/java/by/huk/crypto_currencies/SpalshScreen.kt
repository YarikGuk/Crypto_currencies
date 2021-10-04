package by.huk.crypto_currencies


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.huk.crypto_currencies.data.network.crypto.CryptoService
import by.huk.crypto_currencies.data.repository.CryptoRepository
import by.huk.crypto_currencies.databinding.ActivitySpalshScreenBinding
import by.huk.crypto_currencies.ui.home.HomeFragment
import by.huk.crypto_currencies.ui.utils.applyLoopingAnimatedVectorDrawable
import org.koin.android.ext.android.inject

class SpalshScreen : AppCompatActivity() {

    val e by inject<CryptoService>()


    private lateinit var binding:ActivitySpalshScreenBinding
    private val repository : CryptoRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpalshScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


      binding.animSplashScreen.applyLoopingAnimatedVectorDrawable(R.drawable.custom_progressbar_anim)

        startActivity(Intent(this,MainActivity::class.java))


    }

}