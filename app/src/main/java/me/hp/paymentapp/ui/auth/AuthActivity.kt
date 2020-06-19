package me.hp.paymentapp.ui.auth

import android.os.Bundle
import dagger.android.AndroidInjection
import me.hp.paymentapp.R
import me.hp.paymentapp.ui.base.BaseActivity

class AuthActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }
}
