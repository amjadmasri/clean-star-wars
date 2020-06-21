package com.amjad.starwars.domain.repository

import com.amjad.starwars.domain.models.FilmDomainModel
import io.reactivex.Observable

interface FilmRepository {

    fun getFilmDetails(id: String): Observable<FilmDomainModel>

}