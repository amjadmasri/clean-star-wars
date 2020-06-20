package com.amjad.starwars.data.repository


import com.amjad.starwars.data.extensions.mapNetworkErrors
import com.amjad.starwars.data.local.FilmLocalSource
import com.amjad.starwars.data.mappers.FilmMapper
import com.amjad.starwars.data.models.FilmLocalDataModel
import com.amjad.starwars.data.models.isExpiredAfterOneDay
import com.amjad.starwars.data.remote.FilmRemoteSource
import com.amjad.starwars.domain.models.FilmDomainModel
import com.amjad.starwars.domain.repository.FilmRepository
import io.reactivex.Observable
import javax.inject.Inject

class FilmRepositoryImp @Inject constructor(
    private val filmRemoteSource: FilmRemoteSource,
    private val filmLocalSource: FilmLocalSource,
    private val filmMapper: FilmMapper
) : FilmRepository {
    override fun getFilmDetails(id: String): Observable<FilmDomainModel> {

        return filmLocalSource.getFilmById(id)
            .toObservable()
            .onErrorResumeNext(
                fetchAndCacheFilm(id)
            )
            .concatMap { filmLocalDataModel ->
                return@concatMap if (filmLocalDataModel.isExpiredAfterOneDay()) {
                    fetchAndCacheFilm(id)
                        .map { filmMapper.mapLocalToDomain(it) }
                } else {
                    Observable.just(filmMapper.mapLocalToDomain(filmLocalDataModel))

                }
            }
    }

    private fun fetchAndCacheFilm(id: String): Observable<FilmLocalDataModel> {
        return filmRemoteSource.getFilmDetails(id)
            .mapNetworkErrors()
            .toObservable()
            .flatMap {
                filmLocalSource.insertFilm(filmMapper.mapRemoteToLocal(it))
                    .andThen(Observable.just(filmMapper.mapRemoteToLocal(it)))
            }
    }

}