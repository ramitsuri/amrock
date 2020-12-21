package com.ramitsuri.amrock.credential

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.ramitsuri.amrock.entities.Credentials

class PrefsCredentialStorage(context: Context) : CredentialStorage {
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

    override fun getCredentials(): Credentials {
        val email = securePrefs.getString(EMAIL, "") ?: ""
        val password = securePrefs.getString(PASSWORD, "") ?: ""
        return Credentials(email, password)
    }

    override fun setCredentials(credentials: Credentials) {
        securePrefs.edit()
            .putString(EMAIL, credentials.email)
            .putString(PASSWORD, credentials.password)
            .apply()
    }

    companion object {
        private const val PREF_FILE = "normal_file"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
    }
}