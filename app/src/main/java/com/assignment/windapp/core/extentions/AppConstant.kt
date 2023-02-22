package com.assignment.windapp.core.extentions

const val BASE_URL = "https://wind-assessment-api.vercel.app/api/"
internal const val HEADER_AUTHORIZATION = "Authorization"
const val HEADER_CONTENT_TYPE = "Content-Type"
const val HEADER_CONTENT_TYPE_VALUE = "application/json"
const val HEADER_AUTHORIZATION_TYPE = "Bearer "
const val HEADER_PLATFORM = "x-device-platform"
const val PLATFORM = "ANDROID"
const val HEADER_APP_VERSION = "X-APP-VERSION"

//const val APP_VERSION = "Android/${BuildConfig.VERSION_NAME}"
const val CONNECT_TIMEOUT_S: Long = 60
const val READ_TIMEOUT_S: Long = 60
const val WRITE_TIMEOUT_S: Long = 60

const val APP_PREF = "Android_Pref"