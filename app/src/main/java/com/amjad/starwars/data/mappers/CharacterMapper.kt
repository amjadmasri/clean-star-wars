package com.amjad.starwars.data.mappers

import android.util.Log
import com.amjad.starwars.data.models.CharacterDataModel
import com.amjad.starwars.common.utilities.UrlExtractor
import com.amjad.starwars.domain.models.CharacterDomainModel

open class CharacterMapper :Mapper<CharacterDataModel,CharacterDomainModel>{
    override fun mapFromEntity(entity: CharacterDataModel): CharacterDomainModel {
        Log.d("saed","in map")
        return CharacterDomainModel(entity.birthYear,entity.created,entity.edited,entity.eyeColor,
            UrlExtractor.extractList(entity.films),entity.gender,entity.hairColor,entity.height,
            UrlExtractor.extract(entity.homeworld),entity.mass,entity.name,entity.skinColor,
            UrlExtractor.extractList(entity.species),entity.url)
    }
}