package com.assignment.windapp.domain.repository

import com.assignment.windapp.data.remote.dto.UserDto

interface UserRepository {
    suspend fun getUser(): UserDto
}