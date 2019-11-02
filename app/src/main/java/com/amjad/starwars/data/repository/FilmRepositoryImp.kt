package com.amjad.starwars.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amjad.starwars.data.local.FilmLocalSource
import com.amjad.starwars.data.mappers.FilmMapper
import com.amjad.starwars.data.models.FilmRemoteDataModel
import com.amjad.starwars.data.remote.FilmRemoteSource
import com.amjad.starwars.domain.models.FilmDomainModel
import com.amjad.starwars.domain.repository.FilmRepository
import com.amjad.starwars.presentation.Resource
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class FilmRepositoryImp @Inject constructor(
    private val filmRemoteSource: FilmRemoteSource,
    private val filmLocalSource: FilmLocalSource,
    private val filmMapper: FilmMapper
) : FilmRepository {
    override fun getFilmDetails(id: String): LiveData<Resource<FilmDomainModel>> {
        val result: MutableLiveData<Resource<FilmDomainModel>> = MutableLiveData()
        result.postValue(Resource.loading())
        filmRemoteSource.getFilmDetails(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : SingleObserver<Response<FilmRemoteDataModel>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(response: Response<FilmRemoteDataModel>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        result.postValue(Resource.success(filmMapper.mapFromEntity(data!!)))

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