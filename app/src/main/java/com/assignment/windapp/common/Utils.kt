package com.assignment.windapp.common

/**
 * Created by Saif on 23/02/2023.
 */
object Utils {

    fun userNameValidation(text: String): Boolean {
        val pattern = Regex("^(?!.*[_.]{2})[A-Za-z][A-Za-z_.]{2,}\$")
        return pattern.containsMatchIn(text)
    }

}