package com.amjad.starwars.data.mappers

import com.amjad.starwars.data.models.FilmRemoteDataModel
import com.amjad.starwars.common.utilities.UrlExtractor
import com.amjad.starwars.data.models.FilmLocalDataModel
import com.amjad.starwars.domain.models.FilmDomainModel

open class FilmMapper :Mapper<FilmRemoteDataModel,FilmDomainModel>{
    override fun mapFromEntity(entity: FilmRemoteDataModel): FilmDomainModel {
        return FilmDomainModel(
            UrlExtractor.extractList(entity.characters),entity.created,entity.director,
            entity.edited,entity.episodeId,entity.openingCrawl,entity.producer,
            entity.releaseDate,
            UrlExtractor.extractList(entity.species),entity.title,entity.url)
    }

     fun mapRemoteToLocal(remote: FilmRemoteDataModel): FilmLocalDataModel {
        return FilmLocalDataModel(
           remote.episodeId,remote.created,remote.director,remote.edited,remote.openingCrawl,
            remote.producer,remote.releaseDate,remote.title,remote.url)
    }
}