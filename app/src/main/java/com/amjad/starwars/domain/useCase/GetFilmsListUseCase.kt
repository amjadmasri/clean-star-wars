package com.amjad.starwars.domain.useCase

import com.amjad.starwars.domain.extensions.toResult
import com.amjad.starwars.domain.models.FilmDomainModel
import com.amjad.starwars.domain.models.Result
import com.amjad.starwars.domain.repository.FilmRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetFilmsListUseCase @Inject constructor(private val filmRepository: FilmRepository) {
    fun execute(ids: List<String>): Observable<Result<List<FilmDomainModel>>> {
        val observableList = ids.map { filmRepository.getFilmDetails(it) }

        return Observable.zip(observableList) { filmList ->
            return@zip filmList.map { it as FilmDomainModel }.toResult()
        }
            .onErrorReturn { it.toResult() }


    }
}