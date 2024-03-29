package by.huk.crypto_currencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.huk.crypto_currencies.data.entities.crypto.CryptoEntity
import by.huk.crypto_currencies.data.repository.CryptoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(private val repository: CryptoRepository) : ViewModel() {

    private val _initList = MutableStateFlow<List<CryptoEntity>>(emptyList())
    val initList: StateFlow<List<CryptoEntity>> = _initList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadCryptoList(order: String,page: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = repository.loadCryptoList(order, page)
                _isLoading.value = false
                _initList.value = result
            } catch (e: Exception) {
                _isLoading.value = false
            }
        }

    }
    fun insertInitListToDB(list:List<CryptoEntity>){
        repository.insertCryptoList(list)
    }

}