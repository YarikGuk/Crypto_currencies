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
import java.util.*

class HomeViewModel(private val repository: CryptoRepository) : ViewModel() {


    private val _initList = MutableStateFlow(ListWrapper.NewList(emptyList()))
    val initList: StateFlow<ListWrapper> = _initList

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
                _initList.value = ListWrapper.NewList(result)
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
            }
        }

    }

    private fun loadCryptoListFromDB() {
        _isLoading.value = true
        viewModelScope.launch {
            val cryptoList = repository.loadInitListFromDB()
            _initList.value = ListWrapper.NewList(cryptoList)
            _isLoading.value = false
        }
    }

    fun refreshPageCount() {
        page = SECOND_PAGE
    }


    sealed class ListWrapper{
        data class NewList(val list: List<CryptoEntity>): ListWrapper(){

            override fun equals(other: Any?): Boolean {
                return false
            }

            override fun hashCode(): Int {
                return Random().nextInt()
            }

        }

    }
}

