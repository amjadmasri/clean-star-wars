package com.amjad.starwars.presentation.models

import com.amjad.starwars.domain.models.PlanetDomainModel

data class SpeciesPresentationModel(
    val averageHeight: String,
    val averageLifespan: String,
    val classification: String,
    val created: String,
    val designation: String,
    val edited: String,
    val eyeColors: String,
    val hairColors: String,
    var homeWorld: PlanetDomainModel?,
    val language: String,
    val name: String,
    val skinColors: String,
    val url: String
)