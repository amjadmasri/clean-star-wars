package com.amjad.starwars.domain.useCase

import com.amjad.starwars.domain.models.FilmDomainModel
import com.amjad.starwars.domain.repository.FilmRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetFilmDetailsUseCase @Inject constructor(private val filmRepository: FilmRepository) {

    fun execute(id: String): Observable<FilmDomainModel> {
        return filmRepository.getFilmDetails(id)
    }
}