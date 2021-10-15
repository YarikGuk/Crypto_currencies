package by.huk.crypto_currencies.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.huk.crypto_currencies.data.entities.crypto.CryptoEntity
import by.huk.crypto_currencies.data.repository.CryptoRepository
import by.huk.crypto_currencies.ui.utils.FIRST_PAGE
import by.huk.crypto_currencies.ui.utils.SECOND_PAGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: CryptoRepository) : ViewModel() {


    private val _initList = MutableStateFlow<List<CryptoEntity>>(emptyList())
    val initList: StateFlow<List<CryptoEntity>> = _initList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()


    var page = FIRST_PAGE
    var checkedItem = 0

    init {
        loadCryptoListFromDB()
    }

    fun loadNextPage(order: String) {
        page++
        loadCryptoList(order, page)

    }

    fun loadCryptoList(order: String, perPage: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.loadCryptoList(order, perPage)
                _isLoading.value = false
                _initList.value = result
            } catch (e: Exception) {
                _isLoading.value = false
            }
        }

    }

    private fun loadCryptoListFromDB() {
        _isLoading.value = true
        viewModelScope.launch {
            val characterList = repository.loadInitListFromDB()
            _initList.value = characterList
            _isLoading.value = false
        }
    }

    fun refreshPageCount() {
        page = SECOND_PAGE
    }


}