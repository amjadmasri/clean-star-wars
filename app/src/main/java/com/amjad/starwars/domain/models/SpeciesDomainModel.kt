package com.amjad.starwars.domain.models


data class SpeciesDomainModel(
    val averageHeight: String,
    val averageLifespan: String,
    val classification: String,
    val created: String,
    val designation: String,
    val edited: String,
    val eyeColors: String,
    val films: List<String>,
    val hairColors: String,
    val homeWorld: String,
    val language: String,
    val name: String,
    val people: List<String>,
    val skinColors: String,
    val url: String
)