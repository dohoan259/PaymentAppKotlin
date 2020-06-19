package me.hp.paymentapp.ui.auth.signup

data class SignUpFormState (
    val emailError: Int? = null,
    val existedEmailError: Int? = null,
    val passwordError: Int? = null,
    val firstNameError: Int? = null,
    val lastNameError: Int? = null,
    val birthdayError: Int? = null,
    val isDataValid: Boolean = false
)