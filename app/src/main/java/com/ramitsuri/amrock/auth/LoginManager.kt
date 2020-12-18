package com.ramitsuri.amrock.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.time.Instant

class LoginManager(context: Context) {
    private var loggedIn: Boolean = false
    private var lastUseTime: Instant? = null

    private val securePrefs: SharedPreferences

    init {
        val mainKey = MasterKey.Builder(context.applicationContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        securePrefs = EncryptedSharedPreferences.create(
            context.applicationContext,
            PREF_FILE,
            mainKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun setLoggedIn(loggedIn: Boolean) {
        this.loggedIn = loggedIn
    }

    fun onAppForegrounded(currentTime: Instant) {
        val lastUseTime = lastUseTime
        if (isSessionExpired(currentTime, lastUseTime)) {
            loggedIn = false
        }
    }

    fun onAppBackgrounded(currentTime: Instant) {
        lastUseTime = currentTime
    }

    fun requireLogin(): Boolean {
        return !loggedIn
    }

    fun getCredentials(): Credentials {
        val email = securePrefs.getString(EMAIL, "") ?: ""
        val password = securePrefs.getString(PASSWORD, "") ?: ""
        return Credentials(email, password)
    }

    fun setCredentials(credentials: Credentials) {
        securePrefs.edit()
            .putString(EMAIL, credentials.email)
            .putString(PASSWORD, credentials.password)
            .apply()
    }

    private fun isSessionExpired(currentTime: Instant, lastUseTime: Instant?): Boolean {
        if (lastUseTime == null) {
            return false
        }
        return currentTime.toEpochMilli() - lastUseTime.toEpochMilli() >= SESSION_TIME
    }

    companion object {
        const val PREF_FILE = "login_manager_normal_file"
        const val EMAIL = "login_manager_email"
        const val PASSWORD = "login_manager_password"
        const val SESSION_TIME: Long = 60 * 1000 // 1 Minute in millis
    }
}