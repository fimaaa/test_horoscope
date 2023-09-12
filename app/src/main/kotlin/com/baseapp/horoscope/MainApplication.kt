package com.baseapp.horoscope

import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.baseapp.common.base.BaseApplication
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : BaseApplication(), LifecycleEventObserver {

    var lifeCycleApplication: Lifecycle.State = Lifecycle.State.DESTROYED

    @Inject
    lateinit var caocConfig: CaocConfig.Builder
    override fun onCreate() {
        super.onCreate()
        caocConfig.apply()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(updateBaseContextLocale(base))
    }

    private fun updateBaseContextLocale(context: Context): Context {
        val language = "in" // Indonesian language code
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration: Configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        lifeCycleApplication = source.lifecycle.currentState
    }
}