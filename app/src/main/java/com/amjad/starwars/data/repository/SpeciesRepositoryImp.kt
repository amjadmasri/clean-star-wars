package com.amjad.starwars.data.repository

import com.amjad.starwars.data.extensions.mapNetworkErrors
import com.amjad.starwars.data.mappers.SpeciesMapper
import com.amjad.starwars.data.remote.SpeciesRemoteSource
import com.amjad.starwars.domain.models.SpeciesDomainModel
import com.amjad.starwars.domain.repository.SpeciesRepository
import io.reactivex.Observable
import javax.inject.Inject

class SpeciesRepositoryImp @Inject constructor(
    private val speciesRemoteSource: SpeciesRemoteSource,
    private val speciesMapper: SpeciesMapper
) : SpeciesRepository {
    override fun getSpeciesDetails(id: String): Observable<SpeciesDomainModel> {
        return speciesRemoteSource.getSpeciesDetails(id)
            .mapNetworkErrors()
            .flatMapObservable {
                Observable.just(
                    speciesMapper.mapFromEntity(it)
                )
            }

    }
}