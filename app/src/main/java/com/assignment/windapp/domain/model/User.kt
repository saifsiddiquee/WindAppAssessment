package com.assignment.windapp.domain.model

import com.assignment.windapp.data.remote.dto.Data

data class User(
    val data: Data,
    val messages: List<String>,
    val status: Boolean
)