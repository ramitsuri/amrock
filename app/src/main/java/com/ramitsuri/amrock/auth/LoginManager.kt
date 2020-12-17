package com.ramitsuri.amrock.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.time.Instant
import java.time.temporal.ChronoUnit

class LoginManager(context: Context) {
    private var lastAppBackgroundedTime = Instant.MIN
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

    fun onAppBackgrounded() {
        lastAppBackgroundedTime = Instant.now()
    }

    fun requireLogin(currentTime: Instant): Boolean {
        return currentTime.minus(1, ChronoUnit.MINUTES) >= lastAppBackgroundedTime
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

    companion object {
        const val PREF_FILE = "login_manager_normal_file"
        const val EMAIL = "login_manager_email"
        const val PASSWORD = "login_manager_password"
    }
}