package by.huk.crypto_currencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.huk.crypto_currencies.data.entities.crypto.CryptoEntity
import by.huk.crypto_currencies.data.repository.CryptoRepository
import kotlinx.coroutines.launch

class SplashViewModel(val repository: CryptoRepository) : ViewModel() {

    private val _initList = MutableLiveData<List<CryptoEntity>>()
    val initList: LiveData<List<CryptoEntity>> = _initList

    private val _isLoading = MutableLiveData<Boolean?>()
    val isLoading: LiveData<Boolean?> = _isLoading

    private val _exception = MutableLiveData<String>()
    val exception: LiveData<String> = _exception

    fun loadCryptoList(vsCurrency: String, order: String, perPage: Int, page: Int, sparkline: Boolean, priceChange: String, ) {
        viewModelScope.launch {
            try {
                _isLoading.postValue(true)
                val result = repository.loadCryptoList(vsCurrency, order, perPage, page, sparkline, priceChange)
                _isLoading.postValue(false)
                _initList.postValue(result)
            } catch (e: Exception) {
                _exception.postValue(e.message)
                _isLoading.postValue(false)
            }
        }

    }
    fun insertInitListToDB(list:List<CryptoEntity>){
        repository.insertCryptoList(list)
    }
}