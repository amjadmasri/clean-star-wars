package com.amjad.starwars.data.mappers

import com.amjad.starwars.data.models.SpeciesDataModel
import com.amjad.starwars.common.utilities.UrlExtractor
import com.amjad.starwars.domain.models.SpeciesDomainModel

open class SpeciesMapper :Mapper<SpeciesDataModel,SpeciesDomainModel>{
    override fun mapFromEntity(entity: SpeciesDataModel): SpeciesDomainModel {
        return SpeciesDomainModel(entity.averageHeight,entity.averageLifespan,entity.classification,
            entity.created,entity.designation,entity.edited,entity.eyeColors,
            UrlExtractor.extractList(entity.films),entity.hairColors,entity.homeworld,
            entity.language,entity.name,
            UrlExtractor.extractList(entity.people),entity.skinColors,entity.url)
    }
}