package com.ramitsuri.amrock

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.ramitsuri.amrock.auth.LoginManager
import timber.log.Timber

class App : Application(), LifecycleObserver {
    lateinit var loginManager: LoginManager

    override fun onCreate() {
        super.onCreate()
        initLogging()
        initLoginManager()
        initLifecycleObserver()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun onAppCreated() {
        Timber.i("App created")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onAppForegrounded() {
        Timber.i("App foregrounded")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onAppBackgrounded() {
        Timber.i("App backgrounded")
        loginManager.onAppBackgrounded()
    }

    private fun initLogging() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initLoginManager() {
        loginManager = LoginManager(this)
    }

    private fun initLifecycleObserver() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }
}