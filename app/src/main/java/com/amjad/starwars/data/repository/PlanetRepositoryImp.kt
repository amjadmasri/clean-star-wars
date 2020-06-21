package com.amjad.starwars.data.repository

import com.amjad.starwars.data.extensions.mapNetworkErrors
import com.amjad.starwars.data.mappers.PlanetMapper
import com.amjad.starwars.data.remote.PlanetRemoteSource
import com.amjad.starwars.domain.models.PlanetDomainModel
import com.amjad.starwars.domain.repository.PlanetRepository
import io.reactivex.Observable
import javax.inject.Inject

class PlanetRepositoryImp @Inject constructor(
    private val planetRemoteSource: PlanetRemoteSource,
    private val planetMapper: PlanetMapper
) : PlanetRepository {
    override fun getPlanetDetails(id: String): Observable<PlanetDomainModel> {
        return planetRemoteSource.getPlanetDetails(id)
            .mapNetworkErrors()
            .flatMapObservable {
                Observable.just(planetMapper.mapFromEntity(it))
            }

    }
}