package com.amjad.starwars.domain.useCase

import com.amjad.starwars.domain.extensions.toResult
import com.amjad.starwars.domain.models.FilmDomainModel
import com.amjad.starwars.domain.models.Result
import com.amjad.starwars.domain.repository.FilmRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetFilmDetailsUseCase @Inject constructor(private val filmRepository: FilmRepository) {

    fun execute(id: String): Observable<Result<FilmDomainModel>> {
        return filmRepository.getFilmDetails(id)
            .map { it.toResult() }
            .onErrorReturn { it.toResult() }
    }
}