package com.assignment.windapp.data.repository

import com.assignment.windapp.data.remote.dto.UserDto
import com.assignment.windapp.data.remote.service.ApiService
import com.assignment.windapp.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val api: ApiService) : UserRepository {
    override suspend fun getUser(): UserDto {
        return api.getUserData()
    }
}