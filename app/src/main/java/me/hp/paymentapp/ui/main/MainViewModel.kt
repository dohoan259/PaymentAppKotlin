package me.hp.paymentapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.hp.paymentapp.data.repository.user.UserRepo
import javax.inject.Inject

class MainViewModel @Inject constructor(private val userRepo: UserRepo): ViewModel() {

    private var _isUserLoggedIn: MutableLiveData<Boolean> = MutableLiveData(true)

    val isUserLoggedIn: LiveData<Boolean> = _isUserLoggedIn

    fun start() {
        val email = userRepo.getCurrentUserEmail()

        _isUserLoggedIn.value = email != null
    }
}