package com.amjad.starwars.domain.repository

import com.amjad.starwars.domain.models.PlanetDomainModel
import io.reactivex.Observable

interface PlanetRepository {

    fun getPlanetDetails(id: String): Observable<PlanetDomainModel>
}