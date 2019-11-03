package com.amjad.starwars.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amjad.starwars.data.mappers.CharacterMapper
import com.amjad.starwars.data.models.CharacterDataModel
import com.amjad.starwars.data.models.CharacterSearchResponse
import com.amjad.starwars.data.remote.CharacterRemoteSource
import com.amjad.starwars.domain.models.CharacterDomainModel
import com.amjad.starwars.domain.repository.CharacterRepository
import com.amjad.starwars.common.Resource
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class CharacterRepositoryImp @Inject constructor(
    private val characterRemoteSource: CharacterRemoteSource,
    private val characterMapper: CharacterMapper
) : CharacterRepository {
    override fun searchCharacter(
        name: String,
        page: String
    ): Single<Response<CharacterSearchResponse>> {
        return characterRemoteSource.searchCharacter(name, page)
    }

    override fun getCharacterDetails(id: String): LiveData<Resource<CharacterDomainModel>> {

        val result: MutableLiveData<Resource<CharacterDomainModel>> = MutableLiveData()
        result.postValue(Resource.loading())
        characterRemoteSource.getCharacterDetails(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : SingleObserver<Response<CharacterDataModel>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(response: Response<CharacterDataModel>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        Log.d("saed","in is success on usccesss")
                        result.postValue(Resource.success(characterMapper.mapFromEntity(data!!)))

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