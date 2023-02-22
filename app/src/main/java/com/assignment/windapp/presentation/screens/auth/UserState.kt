package com.assignment.windapp.presentation.screens.auth

import com.assignment.windapp.domain.model.User

data class UserState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String = ""
)