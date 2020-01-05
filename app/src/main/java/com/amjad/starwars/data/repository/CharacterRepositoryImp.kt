package com.amjad.starwars.data.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amjad.starwars.data.mappers.CharacterMapper
import com.amjad.starwars.data.models.CharacterDataModel
import com.amjad.starwars.data.models.CharacterSearchResponse
import com.amjad.starwars.data.remote.CharacterRemoteSource
import com.amjad.starwars.domain.models.CharacterDomainModel
import com.amjad.starwars.domain.repository.CharacterRepository
import com.amjad.starwars.common.models.Resource
import io.reactivex.Observable
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

    override fun getCharacterDetails(id: String): Observable<CharacterDomainModel> {


        return characterRemoteSource.getCharacterDetails(id)
            .flatMapObservable { Observable.just(it.body()?.let { characterDataModel ->
                characterMapper.mapFromEntity(
                    characterDataModel
                )
            }) }

    }
}