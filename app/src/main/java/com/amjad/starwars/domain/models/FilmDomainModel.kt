package com.amjad.starwars.domain.models

data class FilmDomainModel (
    val characters: List<String>,
    val created: String,
    val director: String,
    val edited: String,
    val episodeId: Int,
    val openingCrawl: String,
    val producer: String,
    val releaseDate: String,
    val species: List<String>,
    val title: String,
    val url: String
)