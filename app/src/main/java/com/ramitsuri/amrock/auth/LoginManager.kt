package com.ramitsuri.amrock.auth

import com.ramitsuri.amrock.credential.CredentialStorage
import com.ramitsuri.amrock.entities.Credentials
import java.time.Instant

class LoginManager(private val credentialStorage: CredentialStorage) {
    private var loggedIn: Boolean = false
    private var lastUseTime: Instant? = null

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
        return credentialStorage.getCredentials()
    }

    fun setCredentials(credentials: Credentials) {
        credentialStorage.setCredentials(credentials)
    }

    private fun isSessionExpired(currentTime: Instant, lastUseTime: Instant?): Boolean {
        if (lastUseTime == null) {
            return false
        }
        return currentTime.toEpochMilli() - lastUseTime.toEpochMilli() >= SESSION_TIME
    }

    companion object {
        const val SESSION_TIME: Long = 60 * 1000 // 1 Minute in millis
    }
}