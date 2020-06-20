package com.amjad.starwars.domain.models

class ErrorThrowable(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Throwable(message) {
    fun toError(): StarWarsError = StarWarsError(message, cause)
}