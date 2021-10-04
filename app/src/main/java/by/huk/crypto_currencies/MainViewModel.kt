package by.huk.crypto_currencies

import android.app.Application
import androidx.lifecycle.*
import by.huk.crypto_currencies.data.entities.crypto.CryptoEntity
import by.huk.crypto_currencies.data.repository.CryptoRepository
import by.huk.crypto_currencies.data.repository.crypto.CryptoDataSource
import by.huk.crypto_currencies.data.source.dto.mappers.CryptoResponseMapper
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class MainViewModel(private val repository: CryptoRepository) : ViewModel() {





    private val _initList = MutableLiveData<List<CryptoEntity>>()
    val initList: LiveData<List<CryptoEntity>> = _initList

    private val _isLoading = MutableLiveData<Boolean?>()
    val isLoading: LiveData<Boolean?> = _isLoading

    private val _modifiedList = MutableLiveData<List<CryptoEntity>>()
    val modifiedList: LiveData<List<CryptoEntity>> = _modifiedList

    private val _exception = MutableLiveData<String>()
    val exception: LiveData<String> = _exception

    private val _page = MutableLiveData(2)
    val page: LiveData<Int> = _page


    fun loadNextPage(
        vsCurrency: String,
        order: String,
        perPage: Int,
        sparkline: Boolean,
        priceChange: String,
    ) {
        _page.value = _page.value?.inc()
        loadCryptoList(vsCurrency, order, perPage, page.value!!, sparkline, priceChange)

    }

    fun loadCryptoList(
        vsCurrency: String,
        order: String,
        perPage: Int,
        page: Int,
        sparkline: Boolean,
        priceChange: String,
    ) {
        viewModelScope.launch {
            try {
                _isLoading.postValue(true)
                val result = repository.loadCryptoList(vsCurrency,
                    order,
                    perPage,
                    page,
                    sparkline,
                    priceChange)
                _isLoading.postValue(false)
                _initList.postValue(result)
            } catch (e: Exception) {
                _exception.postValue(e.message)
                _isLoading.postValue(false)
            }
        }


    }

}