package com.amjad.starwars.domain.mappers

import com.amjad.starwars.domain.models.CharacterDomainModel
import com.amjad.starwars.presentation.models.CharacterPresentationModel
import kotlin.math.round

class CharacterPresentationMapper {

     fun fromDomainToPresentation(data: CharacterDomainModel?): CharacterPresentationModel {

        return CharacterPresentationModel(
            data!!.birthYear,
            data.created,
            data.edited,
            data.eyeColor,
            mutableListOf(),
            data.gender,
            data.hairColor,
            data.height,
            calculateHeightFeet(data.height),
            calculateHeightInches(data.height),
            null,
            data.mass,
            data.name,
            data.skinColor,
            null,
            data.url
        )

    }

    private fun calculateHeightInches(height: String): String =(0.3937 * height.toDouble()).round(1).toString()

    private fun calculateHeightFeet(height: String): String =(0.0328  * height.toDouble()).round(1).toString()

    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }
}