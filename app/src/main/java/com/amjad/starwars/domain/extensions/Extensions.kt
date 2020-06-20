package com.amjad.starwars.domain.extensions

import com.amjad.starwars.domain.models.ErrorThrowable
import com.amjad.starwars.domain.models.Result
import com.amjad.starwars.domain.models.StarWarsError
import com.amjad.starwars.domain.models.UnknownError
import io.reactivex.Observable

fun StarWarsError.toThrowable(): ErrorThrowable {
    return ErrorThrowable(message, cause)
}

/**
 * A convenient method for wrapping a Throwable in a Result.
 * Converts a Throwable to a Result.OnError.
 */
fun <T> Throwable.toResult(): Result<T> {
    return when (this) {
        is ErrorThrowable -> Result.OnError(this.toError())
        else -> Result.OnError(UnknownError(cause = this))
    }
}

/**
 * A convenient method for extracting Result.OnSuccess.data from a Result.
 * Converts a Result of T to an Observable of T.
 */
fun <T> Result<T>.toData(): Observable<T> {
    return when (this) {
        is Result.OnSuccess -> Observable.just(this.data)
        is Result.OnError -> Observable.error(this.starWarsError.toThrowable())
    }
}

fun <T> T.toResult(): Result<T> {
    return Result.OnSuccess(this)
}

fun <T> Result<T>.toDataObservable(): Observable<T> {
    return when (this) {
        is Result.OnSuccess -> Observable.just(this.data)
        is Result.OnError -> Observable.error(this.starWarsError.toThrowable())
    }
}

fun <T> List<T>.filterIfNotEmpty(value: List<Any>, predicate: (T) -> Boolean): List<T> {
    return when {
        value.isNullOrEmpty() -> this
        else -> filter { predicate(it) }
    }
}