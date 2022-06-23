package com.zensarnewsapp.utility

import java.util.regex.Pattern

object ValidateRegistration {

    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun isValidString(str: String): Boolean{
        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
    }

    fun isEmailEmpty(str: String?) : Boolean{
        return !str.isNullOrEmpty()
    }

    fun isPasswordEmpty(str: String?) : Boolean{
        return !str.isNullOrEmpty()
    }
}