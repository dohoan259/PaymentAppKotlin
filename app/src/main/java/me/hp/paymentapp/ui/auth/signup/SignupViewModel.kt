package me.hp.paymentapp.ui.auth.signup

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.hp.paymentapp.R
import me.hp.paymentapp.data.entity.UserEntity
import me.hp.paymentapp.data.repository.user.UserRepo
import java.util.regex.Pattern
import javax.inject.Inject

class SignupViewModel @Inject constructor(private val userRepo: UserRepo) : ViewModel() {

    private val _signupForm = MutableLiveData<SignUpFormState>()
    val signupFormState: LiveData<SignUpFormState> = _signupForm

    private var _showMainScreen = MutableLiveData<Boolean>(false)
    val showMainScreen: LiveData<Boolean> = _showMainScreen

    fun onSignUpClicked(email: String, password: String, firstName: String, lastName: String, birthday: String) {
        if (!isEmailValid(email)) {
            _signupForm.value = SignUpFormState(emailError = R.string.invalid_username)
            return
        }
        if (!isPasswordValid(password)) {
            _signupForm.value = SignUpFormState(passwordError = R.string.invalid_password)
            return
        }
        if (!isFirstNameValid(firstName)) {
            _signupForm.value = SignUpFormState(firstNameError = R.string.invalid_first_name)
            return
        }
        if (!isLastNameValid(lastName)) {
            _signupForm.value = SignUpFormState(lastNameError = R.string.invalid_last_name)
            return
        }
        if (!isBirthdayValid(birthday)) {
            _signupForm.value = SignUpFormState(birthdayError = R.string.invalid_birthday)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val newUser = UserEntity(email, password, firstName, lastName, birthday)
            val userId = userRepo.createUser(newUser)
            if (userId > 0) {
                _showMainScreen.postValue(true)
            } else {
                _signupForm.postValue(SignUpFormState(existedEmailError = R.string.existed_email_error))
            }
            _showMainScreen.postValue(userId > 0)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        val pattern = Pattern.compile("^((?=.*\\d)(?=.*[A-Z])(?=.*[a-z]).{8,})\$")
        return pattern.matcher(password).matches()
    }

    private fun isFirstNameValid(firstName: String): Boolean {
        return firstName.isNotBlank()
    }

    private fun isLastNameValid(lastName: String): Boolean {
        return lastName.isNotBlank()
    }

    private fun isBirthdayValid(birthday: String): Boolean {
        return birthday.isNotBlank()
    }
}
