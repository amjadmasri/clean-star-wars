package com.amjad.starwars.data.mappers

import com.amjad.starwars.data.models.PlanetDataModel
import com.amjad.starwars.common.utilities.UrlExtractor
import com.amjad.starwars.domain.models.PlanetDomainModel

open class PlanetMapper :Mapper<PlanetDataModel, PlanetDomainModel>{
    override fun mapFromEntity(entity: PlanetDataModel): PlanetDomainModel {
        return PlanetDomainModel(entity.climate,entity.created,entity.diameter,entity.edited,
            UrlExtractor.extractList(entity.films),
            entity.gravity,entity.name,entity.orbitalPeriod,entity.population,
            UrlExtractor.extractList(entity.residents),entity.rotationPeriod,entity.surfaceWater,entity.terrain,
            entity.url)
    }
}