package com.amjad.starwars.data.extensions

import com.amjad.starwars.domain.extensions.toThrowable
import com.amjad.starwars.domain.models.HttpCallFailureException
import com.amjad.starwars.domain.models.NoNetworkException
import com.amjad.starwars.domain.models.ServerUnreachableException
import com.google.gson.Gson
import io.reactivex.Single
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * maps an error http response of >=400 from Json to a generic error model T
 * return a Single with the value of T after parsing
 * @receiver Single<out R>
 * @return Single<R>
 */
inline fun <reified T : R, R> Single<out R>.mapError(): Single<R> =
    this.map { it }
        .onErrorResumeNext { error ->
            if (error is HttpException && error.code() >= 400) {
                mapErrorBody(error, T::class.java)?.let {
                    Single.just(it)
                } ?: Single.error(IllegalStateException("Mapping http body failed!"))
            } else {
                Single.error(error)
            }
        }

/**
 * deserialize Json error response and returns a data class of type T
 * @param error HttpException
 * @param type Class<T>
 * @return T?
 */
fun <T> mapErrorBody(error: HttpException, type: Class<T>) =
    error.response()?.errorBody()?.let {
        Gson().fromJson(it.string(), type)
    }

fun <T> Single<T>.mapNetworkErrors(): Single<T> {
    return this.onErrorResumeNext { error ->
        when (error) {
            is SocketTimeoutException -> Single.error(NoNetworkException(error).toThrowable())
            is UnknownHostException -> Single.error(ServerUnreachableException(error).toThrowable())
            is ConnectException -> Single.error(NoNetworkException(error).toThrowable())
            is HttpException -> Single.error(HttpCallFailureException(error).toThrowable())
            else -> Single.error(error)
        }
    }
}
