package com.amjad.starwars.data.mappers

import com.amjad.starwars.data.models.CharacterDataModel
import com.amjad.starwars.common.utilities.UrlExtractor
import com.amjad.starwars.domain.models.CharacterDomainModel
import javax.inject.Inject

open class CharacterMapper @Inject constructor(private val urlExtractor: UrlExtractor) :Mapper<CharacterDataModel,CharacterDomainModel>{
    override fun mapFromEntity(entity: CharacterDataModel): CharacterDomainModel {
        return CharacterDomainModel(entity.birthYear,entity.created,entity.edited,entity.eyeColor,
            urlExtractor.extractList(entity.films),entity.gender,entity.hairColor,entity.height,
            urlExtractor.extract(entity.homeworld),entity.mass,entity.name,entity.skinColor,
            urlExtractor.extractList(entity.species),urlExtractor.extract(entity.url))
    }

    fun mapListFromEntity(entitlyList: List<CharacterDataModel>):List<CharacterDomainModel>{

        return entitlyList.map {
            mapFromEntity(it)
        }
    }
}