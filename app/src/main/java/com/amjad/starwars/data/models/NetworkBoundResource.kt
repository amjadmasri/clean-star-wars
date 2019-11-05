package com.amjad.starwars.data.models


import android.annotation.SuppressLint
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.amjad.starwars.common.models.Resource
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

@Suppress("LeakingThis")
abstract class NetworkBoundResource<ResultType, RequestType> @MainThread
constructor() {
    private val result = MediatorLiveData<Resource<ResultType>>()

    val asLiveData: LiveData<Resource<ResultType>>
        get() = result

    init {
        result.value = Resource.loading(null)
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (this@NetworkBoundResource.shouldFetch(data)) {
                this@NetworkBoundResource.fetchFromNetwork(dbSource)
            } else {
                result.addSource(
                    dbSource
                ) { newData -> result.setValue(Resource.success(newData)) }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        result.addSource(
            dbSource
        ) { newData -> result.setValue(Resource.loading(newData)) }
        createCall().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Response<RequestType>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(response: Response<RequestType>) {
                    if (response.isSuccessful) {
                        result.removeSource(dbSource)
                        saveResultAndReInit(response.body())
                    } else {
                        result.setValue(Resource.error("unsuccessful "))
                    }
                }

                override fun onError(e: Throwable) {
                    result.value = Resource.error(e.localizedMessage)
                }
            })
    }

    @SuppressLint("WrongThread")
    @MainThread
    private fun saveResultAndReInit(response: RequestType?) {

        saveCallResult(response!!).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    result.addSource(
                        loadFromDb()
                    ) { newData ->
                        result.setValue(Resource.success(newData))
                    }
                }

                override fun onError(e: Throwable) {
                    result.value = Resource.error(e.localizedMessage)
                }
            })
    }

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType): Completable

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): Single<Response<RequestType>>

    @MainThread
    protected fun onFetchFailed() {
    }
}
