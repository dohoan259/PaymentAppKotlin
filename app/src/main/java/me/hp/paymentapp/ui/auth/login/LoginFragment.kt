package me.hp.paymentapp.ui.auth.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import me.hp.paymentapp.R
import androidx.core.view.ViewCompat
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import kotlinx.android.synthetic.main.fragment_login.*
import me.hp.paymentapp.common.hideSoftKeyboard
import me.hp.paymentapp.ui.base.BaseFragment
import javax.inject.Inject


class LoginFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: LoginViewModel

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        viewModel.showMainScreen.observe(this, Observer { isShow ->
            if (isShow) {
                findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
                requireActivity().finish()
            }
        })
        viewModel.showLoginError.observe(this, Observer { isShow ->
            if (isShow) {
                Toast.makeText(context, "Email or password is invalid", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_login, container, false)

        email = root.findViewById(R.id.email_et)
        password = root.findViewById(R.id.password_et)
        loginBtn = root.findViewById(R.id.login_btn)

        setUpView()

        return root
    }

    private fun setUpView() {

        setListener()
    }

    private fun setListener() {
        loginBtn.setOnClickListener {
            hideSoftKeyboard(requireContext(), loginBtn)
            viewModel.onLoginClicked(
                email.text.trim().toString(),
                password.text.trim().toString()
            )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        scroll_view.systemUiVisibility =
            SYSTEM_UI_FLAG_LAYOUT_STABLE or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        val scrollPaddingLeft = scroll_view.paddingLeft
        val scrollPaddingTop = scroll_view.paddingTop
        val scrollPaddingRight = scroll_view.paddingRight
        val scrollPaddingBottom = scroll_view.paddingBottom
        ViewCompat.setOnApplyWindowInsetsListener(scroll_view) { _, insets->
            scroll_view.setPadding(
                scrollPaddingLeft,
                scrollPaddingTop,
                scrollPaddingRight,
                scrollPaddingBottom + insets.systemWindowInsetBottom
            )
            insets }
        sign_up_btn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }

}
