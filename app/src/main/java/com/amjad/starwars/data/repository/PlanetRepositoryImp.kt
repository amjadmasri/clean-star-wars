package com.amjad.starwars.data.repository

import androidx.lifecycle.MutableLiveData
import com.amjad.starwars.common.models.Resource
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
        val result: MutableLiveData<Resource<PlanetDomainModel>> = MutableLiveData()
        result.postValue(Resource.loading())
        return planetRemoteSource.getPlanetDetails(id)
            .mapNetworkErrors()
            .flatMapObservable {
                Observable.just(planetMapper.mapFromEntity(it))
            }

    }
}