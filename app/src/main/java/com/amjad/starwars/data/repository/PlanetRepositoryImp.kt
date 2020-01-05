package com.amjad.starwars.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amjad.starwars.data.mappers.PlanetMapper
import com.amjad.starwars.data.models.PlanetDataModel
import com.amjad.starwars.data.remote.PlanetRemoteSource
import com.amjad.starwars.domain.models.PlanetDomainModel
import com.amjad.starwars.domain.repository.PlanetRepository
import com.amjad.starwars.common.models.Resource
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class PlanetRepositoryImp @Inject constructor(private val planetRemoteSource: PlanetRemoteSource,private val planetMapper: PlanetMapper) :PlanetRepository{
    override fun getPlanetDetails(id: String): Observable<PlanetDomainModel> {
        val result:MutableLiveData<Resource<PlanetDomainModel>> = MutableLiveData()
        result.postValue(Resource.loading())
        return planetRemoteSource.getPlanetDetails(id)
            .flatMapObservable { Observable.just(it.body()?.let { it1 ->
                planetMapper.mapFromEntity(
                    it1
                )
            }) }

    }
}