package com.ramitsuri.amrock

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.ramitsuri.amrock.dependency.Injector
import timber.log.Timber
import java.time.Instant

class App : Application(), LifecycleObserver {
    lateinit var injector: Injector

    override fun onCreate() {
        super.onCreate()
        instance = this
        initLogging()
        initInjector()
        initLifecycleObserver()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun onAppCreated() {
        Timber.i("App created")
        injector.loginManager.setLoggedIn(false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onAppForegrounded() {
        Timber.i("App foregrounded")
        injector.loginManager.onAppForegrounded(Instant.now())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onAppBackgrounded() {
        Timber.i("App backgrounded")
        injector.loginManager.onAppBackgrounded(Instant.now())
    }

    private fun initInjector() {
        injector = Injector(this)
    }

    private fun initLogging() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initLifecycleObserver() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    companion object {
        lateinit var instance: App
            private set
    }
}