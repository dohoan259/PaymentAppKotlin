package me.hp.paymentapp.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import me.hp.paymentapp.PaymentApp
import me.hp.paymentapp.di.builder.ActivityBuilder
import me.hp.paymentapp.di.module.*
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        RepositoryModule::class,
        ActivityBuilder::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(application: PaymentApp)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}