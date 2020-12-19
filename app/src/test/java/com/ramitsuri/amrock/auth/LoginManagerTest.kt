package com.ramitsuri.amrock.auth

import com.ramitsuri.amrock.credential.CredentialStorage
import com.ramitsuri.amrock.entities.Credentials
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.time.Instant

class LoginManagerTest {
    private lateinit var loginManager: LoginManager

    @Before
    fun setUp() {
        var creds = Credentials("", "")
        val store = object : CredentialStorage {
            override fun getCredentials(): Credentials {
                return creds
            }

            override fun setCredentials(credentials: Credentials) {
                creds = credentials
            }
        }
        loginManager = LoginManager(store)
    }

    @Test
    fun testRequireLogin_shouldReturnTrue_ifInitialState() {
        assertTrue(loginManager.requireLogin()) // Should be true initially

        loginManager.onAppForegrounded(Instant.now()) // App hasn't been backgrounded
        assertTrue(loginManager.requireLogin())
    }

    @Test
    fun testRequireLogin_shouldReturnTrue_ifSessionExpired() {
        loginManager.onAppBackgrounded(Instant.ofEpochMilli(0))
        loginManager.onAppForegrounded(Instant.ofEpochMilli(60 * 1000 + 1))

        assertTrue(loginManager.requireLogin())
    }

    @Test
    fun testRequireLogin_shouldReturnTrue_ifLoggedInAndSessionExpired() {
        loginManager.setLoggedIn(true)
        loginManager.onAppBackgrounded(Instant.ofEpochMilli(0))
        loginManager.onAppForegrounded(Instant.ofEpochMilli(60 * 1000 + 1))

        assertTrue(loginManager.requireLogin())
    }

    @Test
    fun testRequireLogin_shouldReturnTrue_ifNotLoggedInAndSessionNotExpired() {
        loginManager.setLoggedIn(false)
        loginManager.onAppBackgrounded(Instant.ofEpochMilli(0))
        loginManager.onAppForegrounded(Instant.ofEpochMilli(60 * 1000 - 1))

        assertTrue(loginManager.requireLogin())
    }

    @Test
    fun testRequireLogin_shouldReturnFalse_ifLoggedInAndSessionNotExpired() {
        loginManager.setLoggedIn(true)
        loginManager.onAppBackgrounded(Instant.ofEpochMilli(0))
        loginManager.onAppForegrounded(Instant.ofEpochMilli(60 * 1000 - 1))

        assertFalse(loginManager.requireLogin())
    }

    @Test
    fun testGetCredentials_shouldReturnEmpty_ifInitial() {
        assertTrue(loginManager.getCredentials().email.isEmpty())
        assertTrue(loginManager.getCredentials().password.isEmpty())
    }

    @Test
    fun testGetCredentials_shouldReturnNonEmpty_ifSet() {
        loginManager.setCredentials(Credentials("email", "password"))
        assertFalse(loginManager.getCredentials().email.isEmpty())
        assertFalse(loginManager.getCredentials().password.isEmpty())
    }
}