package me.hp.paymentapp.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.hp.paymentapp.data.repository.user.UserRepo
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userRepo: UserRepo): ViewModel() {

    private var _showMainScreen = MutableLiveData<Boolean>(false)
    val showMainScreen: LiveData<Boolean> = _showMainScreen

    private var _showLoginError = MutableLiveData<Boolean>(false)
    val showLoginError: LiveData<Boolean> = _showLoginError

    fun onLoginClicked(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepo.getUserByCredentials(email, password)

            if (user != null) {
                userRepo.saveCredentials(email)
                _showMainScreen.postValue(true)
            } else {
                _showLoginError.postValue(true)
            }
        }
    }
}
