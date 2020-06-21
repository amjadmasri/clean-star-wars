package com.amjad.starwars.data.remote

import androidx.paging.PagedList
import com.amjad.starwars.data.models.CharacterDataModel
import com.amjad.starwars.data.models.CharacterSearchResponse
import com.amjad.starwars.domain.models.CharacterDomainModel
import com.amjad.starwars.domain.models.PagedListing
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response

interface CharacterRemoteSource {

    fun searchCharacter(name :String): Observable<PagedList<CharacterDomainModel>>

    fun getCharacterDetails(id:String):Single<CharacterDataModel>

    fun getMoreCharacters(url:String):Single<CharacterSearchResponse>
}