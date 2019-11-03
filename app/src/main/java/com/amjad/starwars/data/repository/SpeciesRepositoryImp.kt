package com.amjad.starwars.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amjad.starwars.data.mappers.SpeciesMapper
import com.amjad.starwars.data.models.SpeciesDataModel
import com.amjad.starwars.data.remote.SpeciesRemoteSource
import com.amjad.starwars.domain.models.SpeciesDomainModel
import com.amjad.starwars.domain.repository.SpeciesRepository
import com.amjad.starwars.common.Resource
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
    override fun getSpeciesDetails(id: String): LiveData<Resource<SpeciesDomainModel>> {
        val result: MutableLiveData<Resource<SpeciesDomainModel>> = MutableLiveData()
        result.postValue(Resource.loading())
        speciesRemoteSource.getSpeciesDetails(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : SingleObserver<Response<SpeciesDataModel>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(response: Response<SpeciesDataModel>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        result.postValue(Resource.success(speciesMapper.mapFromEntity(data!!)))

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