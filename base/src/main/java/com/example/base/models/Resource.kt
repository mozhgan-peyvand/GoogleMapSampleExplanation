package com.example.base.models


sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val error: Exceptions) : Resource<Nothing>()
}