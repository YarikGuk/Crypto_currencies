package by.huk.crypto_currencies.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.huk.crypto_currencies.data.entities.crypto.MarketChart
import by.huk.crypto_currencies.data.repository.CryptoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: CryptoRepository) : ViewModel() {


    private val _chartList = MutableStateFlow<List<MarketChart>>(emptyList())
    val chartList: StateFlow<List<MarketChart>> = _chartList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()


    fun loadMarketChart(id: String, days: String, interval: String? = null) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = repository.loadMarketChart(id, days, interval)
                _chartList.value = result
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
            }
        }


    }

}