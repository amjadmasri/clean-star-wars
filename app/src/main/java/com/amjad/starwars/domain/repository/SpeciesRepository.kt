package com.amjad.starwars.domain.repository

import com.amjad.starwars.domain.models.SpeciesDomainModel
import io.reactivex.Observable

interface SpeciesRepository {

    fun getSpeciesDetails(id: String): Observable<SpeciesDomainModel>
}