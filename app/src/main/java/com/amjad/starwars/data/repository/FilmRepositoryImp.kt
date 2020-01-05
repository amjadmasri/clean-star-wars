package com.amjad.starwars.data.repository


import android.annotation.SuppressLint
import android.util.Log
import androidx.room.EmptyResultSetException
import com.amjad.starwars.data.local.FilmLocalSource
import com.amjad.starwars.data.mappers.FilmMapper
import com.amjad.starwars.data.models.FilmLocalDataModel
import com.amjad.starwars.data.models.isExpiredAfterOneDay
import com.amjad.starwars.data.remote.FilmRemoteSource
import com.amjad.starwars.domain.models.FilmDomainModel
import com.amjad.starwars.domain.repository.FilmRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class FilmRepositoryImp @Inject constructor(
    private val filmRemoteSource: FilmRemoteSource,
    private val filmLocalSource: FilmLocalSource,
    private val filmMapper: FilmMapper
) : FilmRepository {
    override fun getFilmDetails(id: String): Observable<FilmDomainModel> {

        return filmLocalSource.getFilmById(id)
            .onErrorResumeNext { if(it is EmptyResultSetException) {
                Log.d("amjad","first if empty")
                Single.just(FilmLocalDataModel(1, "", "", "", "", "", "", "", "", "", 0))
            }
            else {
                Log.d("amjad","first else empty")
                Single.error(it)
            }
            }
            .flatMapObservable {
                if (it.localCreationDate==0L && it.isExpiredAfterOneDay()) {
                    Log.d("amjad","here")
                    filmRemoteSource.getFilmDetails(id)
                        .flatMapObservable { response ->
                            filmLocalSource.insertFilm(filmMapper.mapRemoteToLocal(response.body()!!))
                                .subscribe()
                            filmLocalSource.getFilmById(id)
                                .flatMapObservable { filmLocalDataModel ->
                                    Observable.just(
                                        filmMapper.mapLocalToDomain(filmLocalDataModel)
                                    )
                                }
                        }
                }
                else
                    Observable.just(filmMapper.mapLocalToDomain(it))
            }
    }

}