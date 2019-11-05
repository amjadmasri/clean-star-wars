package com.amjad.starwars.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amjad.starwars.data.mappers.PlanetMapper
import com.amjad.starwars.data.models.PlanetDataModel
import com.amjad.starwars.data.remote.PlanetRemoteSource
import com.amjad.starwars.domain.models.PlanetDomainModel
import com.amjad.starwars.domain.repository.PlanetRepository
import com.amjad.starwars.common.models.Resource
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class PlanetRepositoryImp @Inject constructor(private val planetRemoteSource: PlanetRemoteSource,private val planetMapper: PlanetMapper) :PlanetRepository{
    override fun getPlanetDetails(id: String): LiveData<Resource<PlanetDomainModel>> {
        val result:MutableLiveData<Resource<PlanetDomainModel>> = MutableLiveData()
        result.postValue(Resource.loading())
        planetRemoteSource.getPlanetDetails(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : SingleObserver<Response<PlanetDataModel>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(response: Response<PlanetDataModel>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        result.postValue(Resource.success(planetMapper.mapFromEntity(data!!)))

                    } else {
                        result.postValue(Resource.error("there was an error"))
                    }
                }

                override fun onError(e: Throwable) {
                    result.postValue(Resource.error(e.localizedMessage))
                }
            })
        return result
    }
}