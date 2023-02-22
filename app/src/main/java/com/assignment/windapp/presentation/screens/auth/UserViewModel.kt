package com.assignment.windapp.presentation.screens.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.assignment.windapp.domain.usecase.GetUserUseCase
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _state = mutableStateOf(UserState())
    val state: State<UserState> = _state


    private fun getUser(){

    }
}