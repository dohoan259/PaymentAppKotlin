package me.hp.paymentapp.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import me.hp.paymentapp.R
import me.hp.paymentapp.ui.auth.signup.SignupViewModel
import me.hp.paymentapp.ui.base.BaseFragment
import javax.inject.Inject

class ProfileFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ProfileViewModel

    private lateinit var logoutBtn: Button
    private lateinit var deleteAccount: Button
    private lateinit var email: TextView
    private lateinit var firstName: TextView
    private lateinit var lastName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)

        viewModel.showAuthFlow.observe(this, Observer { isShow ->
            if (isShow) {
                findNavController().navigate(R.id.action_navigation_profile_to_authActivity)
                requireActivity().finish()
            }
        })
        viewModel.userData.observe(this, Observer { userProfile ->
            if (userProfile != null) {
                email.text = userProfile.email
                firstName.text = userProfile.firstName
                lastName.text = userProfile.lastName
            }
        })

        viewModel.loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        logoutBtn = root.findViewById(R.id.logout_btn)
        deleteAccount = root.findViewById(R.id.delete_account_btn)
        email = root.findViewById(R.id.email_tv)
        firstName = root.findViewById(R.id.first_name_tv)
        lastName = root.findViewById(R.id.last_name_tv)

        setupView()

        return root
    }

    private fun setupView() {

        setListener()
    }

    private fun setListener() {
        logoutBtn.setOnClickListener {
            viewModel.onLogoutClicked()
        }
    }
}