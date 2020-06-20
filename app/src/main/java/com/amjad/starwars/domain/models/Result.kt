package com.amjad.starwars.domain.models

sealed class Result<out T> {
    data class OnSuccess<out T>(val data: T) : Result<T>()
    data class OnError<out T>(val starWarsError: StarWarsError) : Result<T>()
}