package by.huk.crypto_currencies.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.huk.crypto_currencies.data.entities.user.User
import by.huk.crypto_currencies.data.repository.CryptoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingViewModel(private val repository: CryptoRepository) : ViewModel() {

    private val _user = MutableStateFlow(User("", "", "", ""))
    val user: StateFlow<User> = _user.asStateFlow()

    fun updateUser(user: User) {
        repository.updateUser(user)
    }

    fun loadUser() {
        viewModelScope.launch {
            try {
                val result = repository.loadUserFromDB()
                _user.value = result
            } catch (e: Exception) {
            }
        }
    }
}