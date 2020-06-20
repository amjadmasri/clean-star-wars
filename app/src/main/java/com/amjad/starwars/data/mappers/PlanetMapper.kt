package com.amjad.starwars.data.mappers

import com.amjad.starwars.data.models.PlanetDataModel
import com.amjad.starwars.domain.models.PlanetDomainModel
import javax.inject.Inject

open class PlanetMapper @Inject constructor(){
    fun mapFromEntity(entity: PlanetDataModel): PlanetDomainModel {
        return PlanetDomainModel(entity.climate,entity.created,entity.diameter,entity.edited, entity.films,
            entity.gravity,entity.name,entity.orbitalPeriod,entity.population,
            entity.residents,entity.rotationPeriod,entity.surfaceWater,entity.terrain,
            entity.url)
    }
}