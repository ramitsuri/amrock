package com.ramitsuri.amrock.auth

interface AuthValidator {
    fun isEmailValid(email: CharSequence?): Boolean

    fun isPasswordValid(password: CharSequence?): Boolean
}