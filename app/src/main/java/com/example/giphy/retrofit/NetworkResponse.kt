package com.example.giphy.retrofit

open class NetworkResponse<out T> {
    data class Success<T>(val value: T) : NetworkResponse<T>()
    data class ApiError(val error: String, val code: Int): NetworkResponse<Nothing>()
    data class Failure(val error: Throwable?): NetworkResponse<Nothing>()

}
