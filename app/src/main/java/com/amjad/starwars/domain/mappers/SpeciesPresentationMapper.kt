package com.amjad.starwars.domain.mappers

import com.amjad.starwars.domain.models.SpeciesDomainModel
import com.amjad.starwars.presentation.models.SpeciesPresentationModel
import javax.inject.Inject

class SpeciesPresentationMapper @Inject constructor() {

    fun fromDomainToPresentation(model: SpeciesDomainModel):SpeciesPresentationModel{
        return SpeciesPresentationModel(
            model.averageHeight,
            model.averageLifespan,
            model.classification,
            model.created,
            model.designation,
            model.edited,
            model.eyeColors,
            model.hairColors,
            null,
            model.language,
            model.name,
            model.skinColors,
            model.url
        )
    }
}