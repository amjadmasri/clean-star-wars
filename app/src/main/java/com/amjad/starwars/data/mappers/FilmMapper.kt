package com.amjad.starwars.data.mappers

import com.amjad.starwars.data.models.FilmRemoteDataModel
import com.amjad.starwars.common.utilities.UrlExtractor
import com.amjad.starwars.data.models.FilmLocalDataModel
import com.amjad.starwars.domain.models.FilmDomainModel
import javax.inject.Inject

open class FilmMapper @Inject constructor(private val urlExtractor: UrlExtractor) :Mapper<FilmRemoteDataModel,FilmDomainModel>{
    override fun mapFromEntity(entity: FilmRemoteDataModel): FilmDomainModel {
        return FilmDomainModel(
            urlExtractor.extractList(entity.characters),entity.created,entity.director,
            entity.edited,entity.episodeId,entity.openingCrawl,entity.producer,
            entity.releaseDate,
            urlExtractor.extractList(entity.species),entity.title,entity.url)
    }

     fun mapRemoteToLocal(remote: FilmRemoteDataModel): FilmLocalDataModel {
        return FilmLocalDataModel(
           remote.episodeId,remote.created,remote.director,remote.edited,remote.openingCrawl,
            remote.producer,remote.releaseDate,remote.title,urlExtractor.extract(remote.url),remote.url)
    }

    fun mapLocalToDomain(local: FilmLocalDataModel): FilmDomainModel {
        return FilmDomainModel(
            emptyList(),local.created,local.director,local.edited,local.episodeId,local.openingCrawl,
            local.producer,local.releaseDate, emptyList(),local.title,local.url)
    }
}