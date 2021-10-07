package by.huk.crypto_currencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.huk.crypto_currencies.data.entities.crypto.CryptoEntity
import by.huk.crypto_currencies.data.entities.user.User
import by.huk.crypto_currencies.data.repository.CryptoRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CryptoRepository) : ViewModel() {

    private val _initList = MutableLiveData<List<CryptoEntity>>()
    val initList: LiveData<List<CryptoEntity>> = _initList

    private val _isLoading = MutableLiveData<Boolean?>()
    val isLoading: LiveData<Boolean?> = _isLoading

    private val _modifiedList = MutableLiveData<List<CryptoEntity>>()
    val modifiedList: LiveData<List<CryptoEntity>> = _modifiedList

    private val _exception = MutableLiveData<String>()
    val exception: LiveData<String> = _exception

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _page = MutableLiveData(2)
    val page: LiveData<Int> = _page


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

    fun loadCryptoListFromDB() {
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