package org.interview.remote.models

sealed class Response<T> {
    data class Success<T>(val result: T) : Response<T>()
    data class Error<T>(val message: ErrorContent, val throwable: Throwable? = null) : Response<T>()
}
