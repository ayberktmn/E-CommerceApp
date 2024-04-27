package com.ayberk.e_commerceapp.common

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Fail(val message: String) : Resource<Nothing>()
    data class Error(val throwable: String) : Resource<Nothing>()

    object Loading : Resource<Nothing>()

    class Unspecified<T : Any> : Resource<T>()
}