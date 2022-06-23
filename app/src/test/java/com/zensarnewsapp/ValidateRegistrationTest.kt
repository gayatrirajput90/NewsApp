package com.zensarnewsapp

import com.google.common.truth.Truth.assertThat
import com.zensarnewsapp.utility.ValidateRegistration
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidateRegistrationTest{

    @Test
    fun whenEmailIsValid(){
        val email = "gayatri@gmail.com"
        val result = ValidateRegistration.isValidString(email)
        assertThat(result).isEqualTo(true)

    }

    @Test
    fun whenEmailIsEmpty(){
        val email = ""
        val result = ValidateRegistration.isEmailEmpty(email)
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun whenPasswordIsEmpty(){
        val email = ""
        val result = ValidateRegistration.isPasswordEmpty(email)
        assertThat(result).isEqualTo(false)
    }
}