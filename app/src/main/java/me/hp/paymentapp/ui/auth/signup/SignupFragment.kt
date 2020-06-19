package me.hp.paymentapp.ui.auth.signup

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat

import androidx.core.content.ContextCompat
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import me.hp.paymentapp.R
import me.hp.paymentapp.ui.base.BaseFragment
import javax.inject.Inject
import java.util.*
import android.annotation.SuppressLint
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.navigation.fragment.findNavController
import me.hp.paymentapp.common.hideSoftKeyboard


class SignupFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SignupViewModel

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var birthday: EditText
    private lateinit var signUpBtn: Button
    private lateinit var loginBtn: Button
    private lateinit var scrollView: NestedScrollView
    private lateinit var userAgreementTv: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SignupViewModel::class.java)

        viewModel.signupFormState.observe(this, androidx.lifecycle.Observer {
            val signUpState = it ?: return@Observer

            if (signUpState.emailError != null) {
                email.error = getString(signUpState.emailError)
            }
            if (signUpState.passwordError != null) {
                password.error = getString(signUpState.passwordError)
            }
            if (signUpState.firstNameError != null) {
                firstName.error = getString(signUpState.firstNameError)
            }
            if (signUpState.lastNameError != null) {
                lastName.error = getString(signUpState.lastNameError)
            }
            if (signUpState.birthdayError != null) {
                birthday.error = getString(signUpState.birthdayError)
            }
            if (signUpState.existedEmailError != null) {
                email.error = getString(signUpState.existedEmailError)
            }
        })
        viewModel.showMainScreen.observe(this, androidx.lifecycle.Observer { isShow ->
            if (isShow) {
                findNavController().navigate(R.id.action_signupFragment_to_mainActivity)
                requireActivity().finish()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_sign_up, container, false)

        email = root.findViewById(R.id.email_et)
        password = root.findViewById(R.id.password_et)
        firstName = root.findViewById(R.id.first_name_et)
        lastName = root.findViewById(R.id.last_name_et)
        birthday = root.findViewById(R.id.birthday_et)
        signUpBtn = root.findViewById(R.id.sign_up_btn)
        loginBtn = root.findViewById(R.id.login_btn)
        scrollView = root.findViewById(R.id.scroll_view)
        userAgreementTv = root.findViewById(R.id.user_agreement_and_policy)

        setupView()

        return root
    }

    private fun setupView() {
        scrollView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        val scrollPaddingLeft = scrollView.paddingLeft
        val scrollPaddingTop = scrollView.paddingTop
        val scrollPaddingRight = scrollView.paddingRight
        val scrollPaddingBottom = scrollView.paddingBottom
        ViewCompat.setOnApplyWindowInsetsListener(scrollView) { _, insets->
            scrollView.setPadding(
                scrollPaddingLeft,
                scrollPaddingTop,
                scrollPaddingRight,
                scrollPaddingBottom + insets.systemWindowInsetBottom
            )
            insets }

        val highLightSS = ForegroundColorSpan(ContextCompat.getColor(this.requireContext(), R.color.high_light_text_color))
        val agreementAndPolicyString = String.format(getString(R.string.user_agreement_and_policy, getString(R.string.app_name)))
        val SS = SpannableString(agreementAndPolicyString)
        val userAgreement = getString(R.string.user_agreement)
        val userAgreementIndex = agreementAndPolicyString.indexOf(userAgreement)

        SS.setSpan(highLightSS, userAgreementIndex, userAgreementIndex + userAgreement.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        val privacyPolicy = getString(R.string.privacy_policy)
        val privacyPolicyIndex = agreementAndPolicyString.indexOf(privacyPolicy)
        SS.setSpan(ForegroundColorSpan(ContextCompat.getColor(this.requireContext(), R.color.high_light_text_color)), privacyPolicyIndex, privacyPolicyIndex + privacyPolicy.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        userAgreementTv.text = SS

        setupListener()
    }

    @SuppressLint("SetTextI18n")
    private fun setupListener() {
        birthday.setOnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr.get(Calendar.DAY_OF_MONTH)
            val month = cldr.get(Calendar.MONTH)
            val year = cldr.get(Calendar.YEAR)
            // date picker dialog
            val picker = DatePickerDialog(this@SignupFragment.requireContext(),
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    birthday.setText(
                        "$dayOfMonth/${monthOfYear + 1}/$year"
                    )
                }, year, month, day
            )
            picker.show()
        }

        signUpBtn.setOnClickListener {
            hideSoftKeyboard(requireContext(), signUpBtn)
            viewModel.onSignUpClicked(
                email.text.trim().toString(),
                password.text.trim().toString(),
                firstName.text.trim().toString(),
                lastName.text.trim().toString(),
                birthday.text.trim().toString()
            )
        }

        loginBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}
