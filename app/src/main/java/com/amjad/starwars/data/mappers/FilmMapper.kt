package com.amjad.starwars.data.mappers

import com.amjad.starwars.data.models.FilmRemoteDataModel
import com.amjad.starwars.data.models.FilmLocalDataModel
import com.amjad.starwars.domain.models.FilmDomainModel
import java.util.*
import javax.inject.Inject

open class FilmMapper @Inject constructor(){
    fun mapFromEntity(entity: FilmRemoteDataModel): FilmDomainModel {
        return FilmDomainModel(
            entity.characters,entity.created,entity.director,
            entity.edited,entity.episodeId,entity.openingCrawl,entity.producer,
            entity.releaseDate,
            entity.species,entity.title,entity.url)
    }

     fun mapRemoteToLocal(remote: FilmRemoteDataModel): FilmLocalDataModel {
        return FilmLocalDataModel(
           remote.episodeId,remote.created,remote.director,remote.edited,remote.openingCrawl,
            remote.producer,remote.releaseDate,remote.title,remote.url,remote.url,
            Date().time
        )
    }

    fun mapLocalToDomain(local: FilmLocalDataModel): FilmDomainModel {
        return FilmDomainModel(
            emptyList(),local.created,local.director,local.edited,local.episodeId,local.openingCrawl,
            local.producer,local.releaseDate, emptyList(),local.title,local.url)
    }
}