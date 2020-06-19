package me.hp.paymentapp.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.hp.paymentapp.di.module.ui.AuthActivityModule
import me.hp.paymentapp.di.module.ui.MainActivityModule
import me.hp.paymentapp.di.module.ui.PayActivityModule
import me.hp.paymentapp.ui.auth.AuthActivity
import me.hp.paymentapp.ui.main.MainActivity
import me.hp.paymentapp.ui.payment.PayActivity

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [AuthActivityModule::class])
    abstract fun contributeAuthActivity(): AuthActivity

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [PayActivityModule::class])
    abstract fun contributePayActivity(): PayActivity
}