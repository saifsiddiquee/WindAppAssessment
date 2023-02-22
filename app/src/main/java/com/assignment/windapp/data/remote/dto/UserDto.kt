package com.assignment.windapp.data.remote.dto

import com.assignment.windapp.domain.model.User

data class UserDto(
    val data: Data,
    val messages: List<String>,
    val status: Boolean
)

fun UserDto.toUser(): User {
    return User(
        data = data,
        messages = messages,
        status = status
    )
}