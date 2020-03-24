package com.razbyte.architectureexample

import android.app.Application
import com.razbyte.architectureexample.business.providers.providersModule
import com.razbyte.architectureexample.ui.featuresModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            fragmentFactory()
            modules(
                listOf(appModule, providersModule, featuresModule)
            )
        }
    }

}