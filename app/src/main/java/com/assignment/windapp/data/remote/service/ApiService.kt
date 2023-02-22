package com.assignment.windapp.data.remote.service

import com.assignment.windapp.data.remote.dto.UserDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @POST("v1/login")
    suspend fun getUserData(): UserDto

}