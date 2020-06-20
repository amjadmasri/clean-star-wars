package com.amjad.starwars.domain.models

open class StarWarsError(
    val message: String? = null,
    val cause: Throwable? = null
)