package com.assignment.windapp.presentation.screens.auth

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.windapp.common.Constants
import com.assignment.windapp.common.Resource
import com.assignment.windapp.data.remote.dto.AuthModel
import com.assignment.windapp.domain.model.User
import com.assignment.windapp.domain.usecase.GetUserUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _state = mutableStateOf(UserState())
    val state: State<UserState> = _state

    private val _loginSuccess = mutableStateOf(false)
    val loginSuccess = _loginSuccess

    fun getUser(authModel: AuthModel) {
        getUserUseCase(authModel).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = UserState(user = result.data)
                    _loginSuccess.value = true
                }
                is Resource.Loading -> {
                    _state.value = UserState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value =
                        UserState(error = result.message ?: "An unexpected error occured")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun saveUser(user: User?) {
        val editor = sharedPreferences.edit()
        editor.putString(Constants.USER_DATA, Gson().toJson(user))
        editor.apply()
    }

    fun getUser(): User {
        val userString = sharedPreferences.getString(Constants.USER_DATA, "")
        return Gson().fromJson(userString, User::class.java)
    }

    fun clear() {
        _loginSuccess.value = false
    }
}