package com.amjad.starwars.data.mappers

import com.amjad.starwars.data.models.SpeciesDataModel
import com.amjad.starwars.domain.models.SpeciesDomainModel
import javax.inject.Inject

open class SpeciesMapper @Inject constructor(){
    fun mapFromEntity(entity: SpeciesDataModel): SpeciesDomainModel {
        return SpeciesDomainModel(entity.averageHeight,entity.averageLifespan,entity.classification,
            entity.created,entity.designation,entity.edited,entity.eyeColors,
            entity.films,entity.hairColors,
            entity.homeworld?:"",
            entity.language,entity.name,
            entity.people,entity.skinColors,entity.url)
    }
}