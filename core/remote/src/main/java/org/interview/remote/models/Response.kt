package org.interview.remote.models

sealed class Response<T> {
    data class Success<T>(val result: T) : Response<T>()
    data class Error(val message: ErrorContent, val throwable: Throwable? = null) : Response<Nothing>()
}

fun <T, D> Response<T>.map(successBlock: Response.Success<T>.() -> D): Response<out D> =
    when (this) {
        is Response.Error -> Response.Error(message, throwable)
        is Response.Success -> Response.Success(successBlock())
    }
