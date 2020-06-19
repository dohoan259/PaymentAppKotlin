package me.hp.paymentapp.di.module.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.hp.paymentapp.ui.main.profile.ProfileFragment
import me.hp.paymentapp.ui.main.scan.ScanFragment
import me.hp.paymentapp.ui.main.wallet.WalletFragment

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeWalletFragment(): WalletFragment

    @ContributesAndroidInjector
    abstract fun contributeScanFragment(): ScanFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment
}