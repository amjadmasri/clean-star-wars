package com.amjad.starwars.data.remote

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.amjad.starwars.data.models.CharacterDataModel
import com.amjad.starwars.data.models.CharacterSearchResponse
import com.amjad.starwars.data.repository.CharacterDataSourceFactory
import com.amjad.starwars.domain.models.CharacterDomainModel
import com.amjad.starwars.domain.models.PagedListing
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class CharacterRemoteSourceImp @Inject constructor(
    private val apiService: ApiService,
    private val characterDataSourceFactoryFactory: CharacterDataSourceFactory.Factory
) : CharacterRemoteSource {

    override fun searchCharacter(
        name: String
    ): Observable<PagedList<CharacterDomainModel>> {

        return  RxPagedListBuilder(characterDataSourceFactoryFactory.create(name), 5)
            .buildObservable()
    }

    override fun getCharacterDetails(id: String): Single<CharacterDataModel> {
        return apiService.getCharacterDetails(id)
    }

    override fun getMoreCharacters(url: String): Single<CharacterSearchResponse> {
        return apiService.getMoreCharacters(url)
    }
}