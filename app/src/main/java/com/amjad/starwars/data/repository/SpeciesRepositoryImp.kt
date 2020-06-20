package com.amjad.starwars.data.repository

import androidx.lifecycle.MutableLiveData
import com.amjad.starwars.common.models.Resource
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
        val result: MutableLiveData<Resource<SpeciesDomainModel>> = MutableLiveData()
        result.postValue(Resource.loading())
        return speciesRemoteSource.getSpeciesDetails(id)
            .mapNetworkErrors()
            .flatMapObservable {
                Observable.just(
                    speciesMapper.mapFromEntity(it))
            }

    }
}