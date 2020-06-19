package me.hp.paymentapp

import android.app.Activity
import android.app.Application
import dagger.android.*
import me.hp.paymentapp.di.component.DaggerAppComponent
import javax.inject.Inject

class PaymentApp : Application(), HasActivityInjector{

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.factory().create(this)
            .inject(this)
    }
}