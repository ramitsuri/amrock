package com.ramitsuri.amrock.auth

import java.util.regex.Pattern

class LocalAuthValidator : AuthValidator {
    override fun isEmailValid(email: CharSequence?): Boolean {
        if (email == null || email.isEmpty()) {
            return false
        }
        return EMAIL_PATTERN.matcher(email).matches()
    }

    override fun isPasswordValid(password: CharSequence?): Boolean {
        if (password == null || password.isEmpty()) {
            return false
        }
        return PASSWORD_PATTERN.matcher(password).matches()
    }

    companion object {
        private val EMAIL_PATTERN: Pattern =
            Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")

        private val PASSWORD_PATTERN: Pattern =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{9,}$")
    }
}