package me.hp.paymentapp.di.module

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import me.hp.paymentapp.common.PREF_NAME
import me.hp.paymentapp.data.local.PayAppDatabase
import me.hp.paymentapp.di.qualifier.AppContext
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @AppContext
    fun provideAppContext(application: Application): Context = application

    @Singleton
    @Provides
    fun provideDatabase(@AppContext context: Context): PayAppDatabase {
        return PayAppDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideSharePref(@AppContext context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
    }
}