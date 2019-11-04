package com.amjad.starwars.data.mappers

import com.amjad.starwars.data.models.SpeciesDataModel
import com.amjad.starwars.common.utilities.UrlExtractor
import com.amjad.starwars.domain.models.SpeciesDomainModel
import javax.inject.Inject

open class SpeciesMapper @Inject constructor(private val urlExtractor: UrlExtractor) :Mapper<SpeciesDataModel,SpeciesDomainModel>{
    override fun mapFromEntity(entity: SpeciesDataModel): SpeciesDomainModel {
        return SpeciesDomainModel(entity.averageHeight,entity.averageLifespan,entity.classification,
            entity.created,entity.designation,entity.edited,entity.eyeColors,
            urlExtractor.extractList(entity.films),entity.hairColors,entity.homeworld?:"",
            entity.language,entity.name,
            urlExtractor.extractList(entity.people),entity.skinColors,entity.url)
    }
}