package by.huk.crypto_currencies

import androidx.lifecycle.ViewModel
import by.huk.crypto_currencies.data.entities.user.User
import by.huk.crypto_currencies.data.repository.CryptoRepository

class MainViewModel(private val repository: CryptoRepository) : ViewModel() {

    fun insertUser(user: User) {
        repository.insertUser(user)
    }



}