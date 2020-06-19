package me.hp.paymentapp.di.module.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.hp.paymentapp.ui.auth.login.LoginFragment
import me.hp.paymentapp.ui.auth.signup.SignupFragment

@Module
abstract class AuthActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeSignupFragment(): SignupFragment
}