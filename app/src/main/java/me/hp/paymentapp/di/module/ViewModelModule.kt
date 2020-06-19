package me.hp.paymentapp.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.hp.paymentapp.ui.auth.login.LoginViewModel
import me.hp.paymentapp.ui.auth.signup.SignupViewModel
import me.hp.paymentapp.ui.main.MainViewModel
import me.hp.paymentapp.ui.main.profile.ProfileViewModel
import me.hp.paymentapp.ui.main.scan.ScanViewModel
import me.hp.paymentapp.ui.main.wallet.WalletViewModel
import me.hp.paymentapp.ui.payment.PayViewModel

@Module
interface
ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun loginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignupViewModel::class)
    fun signupViewModel(signupViewModel: SignupViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun mainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WalletViewModel::class)
    fun walletViewModel(walletViewModel: WalletViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ScanViewModel::class)
    fun scanViewModel(scanViewModel: ScanViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun profileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PayViewModel::class)
    fun payViewModel(payViewModel: PayViewModel): ViewModel

}