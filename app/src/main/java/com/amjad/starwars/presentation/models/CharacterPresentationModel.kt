package com.amjad.starwars.presentation.models

import com.amjad.starwars.domain.models.FilmDomainModel
import com.amjad.starwars.domain.models.PlanetDomainModel
import com.amjad.starwars.domain.models.SpeciesDomainModel

data class CharacterPresentationModel(
    val birthYear: String= "",
    val created: String= "",
    val edited: String= "",
    val eyeColor: String= "",
    var films: MutableList<FilmDomainModel> = mutableListOf(),
    val gender: String= "",
    val hairColor: String= "",
    val heightInCm: String= "",
    val heightFeet:String="",
    val heightInches:String="",
    var homeworld: PlanetDomainModel? =null,
    val mass: String= "",
    val name: String= "",
    val skinColor: String= "",
    var species: SpeciesDomainModel? = null,
    val url: String= ""
)