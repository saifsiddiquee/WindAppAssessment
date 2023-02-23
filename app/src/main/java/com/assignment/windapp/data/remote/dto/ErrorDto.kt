package com.assignment.windapp.data.remote.dto

data class ErrorDto(
    val data: Any,
    val messages: List<String>,
    val status: Boolean
)