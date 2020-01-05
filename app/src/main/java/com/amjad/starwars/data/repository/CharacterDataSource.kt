package com.amjad.starwars.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.amjad.starwars.common.models.Resource
import com.amjad.starwars.common.utilities.UrlExtractor
import com.amjad.starwars.data.mappers.CharacterMapper
import com.amjad.starwars.data.models.CharacterSearchResponse
import com.amjad.starwars.data.remote.CharacterRemoteSource
import com.amjad.starwars.domain.models.CharacterDomainModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class CharacterDataSource @Inject constructor(private val characterRemoteSource: CharacterRemoteSource,private val urlExtractor: UrlExtractor,val characterMapper: CharacterMapper) :
    PageKeyedDataSource<String, CharacterDomainModel>() {

    private lateinit var name: String
    val networkState = MutableLiveData<Resource<String>>()

    fun setSearchParameter(name :String){
        this.name=name
    }

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, CharacterDomainModel>
    ) {
        networkState.postValue(Resource.loading())
        characterRemoteSource.searchCharacter(name,"1")
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(object : SingleObserver<Response<CharacterSearchResponse>> {
               override fun onSubscribe(d: Disposable) {

               }

               override fun onSuccess(response: Response<CharacterSearchResponse>) {
                   if (response.isSuccessful) {
                       val data = response.body()
                       val items = data?.characters

                       networkState.postValue(Resource.success("loaded"))

                       callback.onResult(characterMapper.mapListFromEntity(items!!) as MutableList<CharacterDomainModel>,"0",
                           data.next?.let { urlExtractor.extractPage(data.next) })
                   } else {
                        networkState.postValue(Resource.error("failed"))
                   }
               }

               override fun onError(e: Throwable) {
                   networkState.postValue(Resource.error(e.localizedMessage))

               }
           })
    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<String, CharacterDomainModel>
    ) {
        networkState.postValue(Resource.loading())
        characterRemoteSource.searchCharacter(name,params.key).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Response<CharacterSearchResponse>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(response: Response<CharacterSearchResponse>) {
                    if (response.isSuccessful) {
                        networkState.postValue(Resource.success("loaded"))
                        val data = response.body()
                        val items = data?.characters


                        callback.onResult(characterMapper.mapListFromEntity(items!!) as MutableList<CharacterDomainModel>,  data.next?.let { urlExtractor.extractPage(data.next) })
                    } else {
                        networkState.postValue(Resource.error("failed"))
                    }
                }

                override fun onError(e: Throwable) {
                    networkState.postValue(Resource.error(e.localizedMessage))

                }
            })
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, CharacterDomainModel>
    ) {
        //left empty
    }
}