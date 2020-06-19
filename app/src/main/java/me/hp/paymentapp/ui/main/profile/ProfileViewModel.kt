package me.hp.paymentapp.ui.main.profile


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.hp.paymentapp.data.repository.user.UserRepo
import me.hp.paymentapp.data.uimodel.UserProfile
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val userRepo: UserRepo
): ViewModel() {

    private var _showAuthFlow = MutableLiveData<Boolean>(false)
    val showAuthFlow = _showAuthFlow

    private var _userData = MutableLiveData<UserProfile>(null)
    val userData = _userData

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.getCurrentUser()?.let {
                _userData.postValue(UserProfile(email = it.email, firstName = it.firstName, lastName = it.lastName))
            }
        }
    }

    fun onLogoutClicked() {
        userRepo.logout()
        _showAuthFlow.value = true
    }
}