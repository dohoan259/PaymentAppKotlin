package me.hp.paymentapp.ui.base

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerAppCompatActivity
import me.hp.paymentapp.common.changeStatusBarColor
import me.hp.paymentapp.common.changeStatusBarIconColor

open class BaseActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        AndroidInjection.inject(this)
        changeStatusBarIconColor(this, false)
        changeStatusBarColor(this, Color.TRANSPARENT)
    }
}