package com.example.workmateapp.common

import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorHandler {
    fun handleError(throwable: Throwable): String {
        return when (throwable) {
            is UnknownHostException -> "Нет подключения к интернету"
            is SocketTimeoutException -> "Превышено время ожидания"
            is IOException -> "Ошибка сети"
            else -> "Произошла ошибка: ${throwable.message}"
        }
    }
}

