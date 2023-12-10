package com.example.rockandmorty.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.net.ConnectException

object IOService {

    inline fun <T, R> getRemoteData(
        crossinline request: suspend () -> Response<T>, crossinline mapper: (T) -> R
    ): Flow<ResourceState<R>> = flow {
        emit(ResourceState.Loading())
        val response = request()
        if (response.isSuccessful && response.body() != null) {
            emit(ResourceState.Success(mapper(response.body()!!)))
        } else {
            emit(ResourceState.Error(message = response.message()))
        }
    }.catch { e ->
        when (e) {
            is ConnectException -> emit(ResourceState.ConnectionError(message = "Network connection problem."))

            else -> emit(ResourceState.Error(message = e.message ?: "Something went wrong."))
        }
    }
}