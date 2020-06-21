package com.amjad.starwars.presentation.models

import com.amjad.starwars.domain.models.FilmDomainModel
import com.amjad.starwars.domain.models.PlanetDomainModel
import com.amjad.starwars.domain.models.SpeciesDomainModel

data class CharacterPresentationModel(
    val birthYear: String,
    val created: String,
    val edited: String,
    val eyeColor: String,
    var films: List<FilmDomainModel> = listOf(),
    val gender: String,
    val hairColor: String,
    val heightInCm: String,
    val heightFeet:String,
    val heightInches:String,
    var homeWorld: PlanetDomainModel? =null,
    val mass: String,
    val name: String,
    val skinColor: String,
    var species: SpeciesPresentationModel? = null,
    val url: String
)