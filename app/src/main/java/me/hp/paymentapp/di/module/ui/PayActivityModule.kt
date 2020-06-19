package me.hp.paymentapp.di.module.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.hp.paymentapp.ui.payment.PayFragment

@Module
abstract class PayActivityModule {
    @ContributesAndroidInjector
    abstract fun contributePayFragment(): PayFragment
}