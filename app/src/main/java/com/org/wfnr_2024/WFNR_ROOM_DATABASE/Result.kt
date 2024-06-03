package com.org.wfnr_2024.WFNR_ROOM_DATABASE

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}
