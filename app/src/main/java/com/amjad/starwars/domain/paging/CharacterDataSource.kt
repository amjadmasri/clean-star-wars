package com.amjad.starwars.domain.paging

import androidx.paging.PageKeyedDataSource
import com.amjad.starwars.common.utilities.UrlExtractor
import com.amjad.starwars.data.models.CharacterDataModel
import com.amjad.starwars.data.models.CharacterSearchResponse
import com.amjad.starwars.data.remote.CharacterRemoteSource
import com.amjad.starwars.domain.repository.CharacterRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class CharacterDataSource @Inject constructor(private val characterRemoteSource: CharacterRemoteSource,private val urlExtractor: UrlExtractor) :
    PageKeyedDataSource<String, CharacterDataModel>() {

    private lateinit var name: String

    fun setSearchParameter(name :String){
        this.name=name
    }

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, CharacterDataModel>
    ) {
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

                       callback.onResult(items as MutableList<CharacterDataModel>,"0",
                           data.next?.let { urlExtractor.extractPage(data.next) })
                   } else {

                   }
               }

               override fun onError(e: Throwable) {
                   Timber.tag("amjadF")
                   Timber.d(e)

               }
           })
    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<String, CharacterDataModel>
    ) {
        characterRemoteSource.searchCharacter(name,params.key).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Response<CharacterSearchResponse>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(response: Response<CharacterSearchResponse>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        val items = data?.characters

                        callback.onResult(items as MutableList<CharacterDataModel>,  data.next?.let { urlExtractor.extractPage(data.next) })
                    } else {

                    }
                }

                override fun onError(e: Throwable) {
                    Timber.tag("amjadF")
                    Timber.d(e)

                }
            })
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, CharacterDataModel>
    ) {
        //left empty
    }
}