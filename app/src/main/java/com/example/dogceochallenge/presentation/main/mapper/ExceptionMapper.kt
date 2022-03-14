package com.example.dogceochallenge.presentation.main.mapper

import com.example.dogceochallenge.base.Result
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownServiceException

object ExceptionMapper {

    fun setErrorResult(throwable: Throwable): Result.Error {
        return when (throwable) {
            is HttpException -> Result.Error(throwable)
            is InterruptedIOException -> Result.Error(throwable)
            is SocketException -> Result.Error(throwable)
            is SocketTimeoutException -> Result.Error(throwable)
            is UnknownServiceException -> Result.Error(throwable)
            is JsonDataException -> Result.Error(throwable)
            else -> Result.Error(Exception("Unknown error"))
        }
    }

}
