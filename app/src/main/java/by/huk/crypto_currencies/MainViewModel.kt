package by.huk.crypto_currencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.huk.crypto_currencies.data.entities.crypto.CryptoEntity
import by.huk.crypto_currencies.data.entities.crypto.MarketChart
import by.huk.crypto_currencies.data.entities.user.User
import by.huk.crypto_currencies.data.repository.CryptoRepository
import com.google.android.material.transition.MaterialArcMotion
import com.yabu.livechart.model.DataPoint
import kotlinx.coroutines.launch
//TODO !REFACTORING! Each fragment or activity has to have own viewmodel class
class MainViewModel(private val repository: CryptoRepository) : ViewModel() {

    private val _initList = MutableLiveData<List<CryptoEntity>>()
    val initList: LiveData<List<CryptoEntity>> = _initList

    private val _isLoading = MutableLiveData<Boolean?>()
    val isLoading: LiveData<Boolean?> = _isLoading

    private val _chartList = MutableLiveData<List<MarketChart>>()
    val chartList: LiveData<List<MarketChart>> = _chartList

    private val _exception = MutableLiveData<String>()
    val exception: LiveData<String> = _exception

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _page = MutableLiveData(2)  //TODO !REFACTORING! 2 it's a magic number. Need to move it to constant in the same class with informative name
    val page: LiveData<Int> = _page

    var checkedItem = 0

    init {
        loadCryptoListFromDB()
    }

    fun loadNextPage(order: String) {
        _page.value = _page.value?.inc()
        loadCryptoList(order,page.value!!)

    }

    fun loadCryptoList(order: String, perPage: Int) {
        viewModelScope.launch {
            try {
                _isLoading.postValue(true)
                val result = repository.loadCryptoList(order, perPage)
                _isLoading.postValue(false)
                _initList.postValue(result)
            } catch (e: Exception) {
                _exception.postValue(e.message)
                _isLoading.postValue(false)
            }
        }


    }
    fun loadMarketChart(id:String,days:String,interval:String? = null) {
        viewModelScope.launch {
            try {
                _isLoading.postValue(true)
                val result = repository.loadMarketChart(id,days,interval)
                _isLoading.postValue(false)
                _chartList.postValue(result)
            } catch (e: Exception) {
                _exception.postValue(e.message)
                _isLoading.postValue(false)
            }
        }


    }

    private fun loadCryptoListFromDB() {
        _isLoading.value = true
        viewModelScope.launch {
            val characterList = repository.loadInitListFromDB()
            _initList.postValue(characterList)
            _isLoading.postValue(false)
        }
    }
    fun insertUser(user: User){
        repository.insertUser(user)
    }
    fun updateUser(user: User){
        repository.updateUser(user)
    }
    fun loadUser(){
        viewModelScope.launch {
            try {
                val result = repository.loadUserFromDB()
                _user.postValue(result)
            } catch (e: Exception) {
            }
        }
    }

    fun refreshPageCount() {
        _page.value = 2
    }



}