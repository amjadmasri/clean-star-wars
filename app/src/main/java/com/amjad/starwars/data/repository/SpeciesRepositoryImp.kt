package com.amjad.starwars.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amjad.starwars.data.mappers.SpeciesMapper
import com.amjad.starwars.data.models.SpeciesDataModel
import com.amjad.starwars.data.remote.SpeciesRemoteSource
import com.amjad.starwars.domain.models.SpeciesDomainModel
import com.amjad.starwars.domain.repository.SpeciesRepository
import com.amjad.starwars.common.models.Resource
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class SpeciesRepositoryImp @Inject constructor(
    private val speciesRemoteSource: SpeciesRemoteSource,
    private val speciesMapper: SpeciesMapper
) : SpeciesRepository {
    override fun getSpeciesDetails(id: String): Observable<SpeciesDomainModel> {
        val result: MutableLiveData<Resource<SpeciesDomainModel>> = MutableLiveData()
        result.postValue(Resource.loading())
       return speciesRemoteSource.getSpeciesDetails(id)
           .flatMapObservable { Observable.just(it.body()?.let { it1 ->
               speciesMapper.mapFromEntity(
                   it1
               )
           }) }

    }
}