package by.huk.crypto_currencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.huk.crypto_currencies.data.entities.crypto.CryptoEntity
import by.huk.crypto_currencies.data.entities.user.User
import by.huk.crypto_currencies.data.repository.CryptoRepository
import kotlinx.coroutines.launch
//TODO !REFACTORING! Check IDE hints in classes
class SplashViewModel(val repository: CryptoRepository) : ViewModel() {
    //TODO !REFACTORING! We need to use Kotlin Flow instead of LiveData in all needed classes (it was described in technical task)
    private val _initList = MutableLiveData<List<CryptoEntity>>()
    val initList: LiveData<List<CryptoEntity>> = _initList

    private val _isLoading = MutableLiveData<Boolean?>()
    val isLoading: LiveData<Boolean?> = _isLoading

    fun loadCryptoList(order: String,page: Int) {
        viewModelScope.launch {
            try {
                _isLoading.postValue(true)
                val result = repository.loadCryptoList(order, page)
                _isLoading.postValue(false)
                _initList.postValue(result)
            } catch (e: Exception) {
                _isLoading.postValue(false)
            }
        }

    }
    fun insertInitListToDB(list:List<CryptoEntity>){
        repository.insertCryptoList(list)
    }

}