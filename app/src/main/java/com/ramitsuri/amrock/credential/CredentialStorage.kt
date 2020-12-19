package com.ramitsuri.amrock.credential

import com.ramitsuri.amrock.entities.Credentials

interface CredentialStorage {
    fun getCredentials(): Credentials

    fun setCredentials(credentials: Credentials)
}