package com.ramitsuri.amrock.auth

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LocalAuthValidatorTest {
    private val validator = LocalAuthValidator()

    @Test
    fun testIsEmailValid_shouldReturnFalse_ifNull() {
        assertFalse(validator.isEmailValid(null))
    }

    @Test
    fun testIsEmailValid_shouldReturnFalse_ifEmpty() {
        val email = ""
        assertFalse(validator.isEmailValid(email))
    }

    @Test
    fun testIsEmailValid_shouldReturnFalse_ifInvalid() {
        var email = "john"
        assertFalse(validator.isEmailValid(email))

        email = "john@"
        assertFalse(validator.isEmailValid(email))

        email = "john@gmail"
        assertFalse(validator.isEmailValid(email))

        email = "john@gmail."
        assertFalse(validator.isEmailValid(email))

        email = "john@gmail@"
        assertFalse(validator.isEmailValid(email))

        email = "john@gmail@com"
        assertFalse(validator.isEmailValid(email))

        email = "john@gmail.com."
        assertFalse(validator.isEmailValid(email))

        email = "john.gmail.com"
        assertFalse(validator.isEmailValid(email))
    }

    @Test
    fun testIsEmailValid_shouldReturnTrue_ifValid() {
        var email = "john@gmail.com"
        assertTrue(validator.isEmailValid(email))

        email = "john123@gmail.com"
        assertTrue(validator.isEmailValid(email))

        email = "johnDOE@gmail.com"
        assertTrue(validator.isEmailValid(email))
    }


    @Test
    fun testIsPasswordValid_shouldReturnFalse_ifNull() {
        assertFalse(validator.isPasswordValid(null))
    }

    @Test
    fun testIsPasswordValid_shouldReturnFalse_ifEmpty() {
        assertFalse(validator.isPasswordValid("s"))
    }

    @Test
    fun testIsPasswordValid_shouldReturnFalse_ifNoAlphabet() {
        val password = "123456789"
        assertFalse(validator.isPasswordValid(password))
    }

    @Test
    fun testIsPasswordValid_shouldReturnFalse_ifNoUpper() {
        var password = "a123456789"
        assertFalse(validator.isPasswordValid(password))

        password = "aaaaaaaaa"
        assertFalse(validator.isPasswordValid(password))
    }

    @Test
    fun testIsPasswordValid_shouldReturnFalse_ifNoLower() {
        var password = "A123456789"
        assertFalse(validator.isPasswordValid(password))

        password = "AAAAAAAAA"
        assertFalse(validator.isPasswordValid(password))
    }

    @Test
    fun testIsPasswordValid_shouldReturnFalse_ifNoNumber() {
        var password = "aaaaaAAAA"
        assertFalse(validator.isPasswordValid(password))

        password = "aaaaAAAA@"
        assertFalse(validator.isPasswordValid(password))
    }

    @Test
    fun testIsPasswordValid_shouldReturnTrue_ifValid() {
        var password = "Aaaaaaaa1"
        assertTrue(validator.isPasswordValid(password))

        password = "Aaaaaaaa11"
        assertTrue(validator.isPasswordValid(password))

        password = "1Aaaaaaaa"
        assertTrue(validator.isPasswordValid(password))

        password = "Aaaaaaaa11@"
        assertTrue(validator.isPasswordValid(password))
    }
}