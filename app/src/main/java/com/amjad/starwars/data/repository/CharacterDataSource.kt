package com.amjad.starwars.data.repository

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.amjad.starwars.common.models.Resource
import com.amjad.starwars.data.mappers.CharacterMapper
import com.amjad.starwars.data.models.CharacterSearchResponse
import com.amjad.starwars.data.remote.CharacterRemoteSource
import com.amjad.starwars.domain.models.CharacterDomainModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CharacterDataSource @AssistedInject constructor(
    private val characterRemoteSource: CharacterRemoteSource,
    val characterMapper: CharacterMapper,
    @Assisted private val name: String
) :
    PageKeyedDataSource<String, CharacterDomainModel>() {

    val networkState = MutableLiveData<Resource<String>>()


    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, CharacterDomainModel>
    ) {
        networkState.postValue(Resource.loading())
        characterRemoteSource.searchCharacter(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<CharacterSearchResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(response: CharacterSearchResponse) {
                    networkState.postValue(Resource.success("loaded"))
                    callback.onResult(
                        characterMapper.mapListFromEntity(response.characters), "0",
                        response.next
                    )
                }

                override fun onError(e: Throwable) {
                    networkState.postValue(Resource.error(e.localizedMessage!!))

                }
            })
    }

    @SuppressLint("CheckResult")
    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<String, CharacterDomainModel>
    ) {
        networkState.postValue(Resource.loading())
        characterRemoteSource.getMoreCharacters(params.key).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    networkState.postValue(Resource.success("loaded"))
                    callback.onResult(
                        characterMapper.mapListFromEntity(response.characters),
                        response.next
                    )
                }
                ,
                {
                    networkState.postValue(Resource.error(it.localizedMessage!!))

                }
            )
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, CharacterDomainModel>
    ) {
        //left empty
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(name: String): CharacterDataSource
    }
}