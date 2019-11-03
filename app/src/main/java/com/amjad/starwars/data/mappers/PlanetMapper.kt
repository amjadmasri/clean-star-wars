package com.amjad.starwars.data.mappers

import com.amjad.starwars.data.models.PlanetDataModel
import com.amjad.starwars.common.utilities.UrlExtractor
import com.amjad.starwars.domain.models.PlanetDomainModel
import javax.inject.Inject

open class PlanetMapper @Inject constructor(private val urlExtractor: UrlExtractor) :Mapper<PlanetDataModel, PlanetDomainModel>{
    override fun mapFromEntity(entity: PlanetDataModel): PlanetDomainModel {
        return PlanetDomainModel(entity.climate,entity.created,entity.diameter,entity.edited, urlExtractor.extractList(entity.films),
            entity.gravity,entity.name,entity.orbitalPeriod,entity.population,
            urlExtractor.extractList(entity.residents),entity.rotationPeriod,entity.surfaceWater,entity.terrain,
            entity.url)
    }
}