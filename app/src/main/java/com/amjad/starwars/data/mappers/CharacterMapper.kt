package com.amjad.starwars.data.mappers

import com.amjad.starwars.data.models.CharacterDataModel
import com.amjad.starwars.domain.models.CharacterDomainModel
import javax.inject.Inject

open class CharacterMapper @Inject constructor(){
    fun mapFromEntity(entity: CharacterDataModel): CharacterDomainModel {
        return CharacterDomainModel(entity.birthYear,entity.created,entity.edited,entity.eyeColor,
            entity.films,entity.gender,entity.hairColor,entity.height,
            entity.homeworld,entity.mass,entity.name,entity.skinColor,
            entity.species,entity.url)
    }

    fun mapListFromEntity(entityList: List<CharacterDataModel>):List<CharacterDomainModel>{

        return entityList.map {
            mapFromEntity(it)
        }
    }
}