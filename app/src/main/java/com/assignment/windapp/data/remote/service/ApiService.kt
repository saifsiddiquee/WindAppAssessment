package com.assignment.windapp.data.remote.service

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    @Headers("")
    @GET("")
    suspend fun getData(): ResponseBody
}