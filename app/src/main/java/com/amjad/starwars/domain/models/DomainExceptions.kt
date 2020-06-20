package com.amjad.starwars.domain.models

/**
 * Domain  Exception Classes, created so that the Domain Layer doesn't depend on Network Exception from the Data
 * layer, so it can be reused without depending on framework specific Exceptions
 * also can handle domain specific application exceptions
 */

class NoNetworkException(error: Throwable) : StarWarsError("No Network Available", error)

class ServerUnreachableException(error: Throwable) :
    StarWarsError("Please check your Internet Connection", error)

class HttpCallFailureException(error: Throwable) : StarWarsError("There was a Network Issue", error)

class UnknownError(message: String? = null, cause: Throwable? = null) :
    StarWarsError(message ?: "Unknown error", cause)
