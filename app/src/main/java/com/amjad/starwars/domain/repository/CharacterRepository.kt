package com.amjad.starwars.domain.repository

import com.amjad.starwars.data.models.CharacterSearchResponse
import com.amjad.starwars.domain.models.CharacterDomainModel
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response

interface CharacterRepository {

    fun searchCharacter(name: String): Single<CharacterSearchResponse>

    fun getCharacterDetails(id: String): Observable<CharacterDomainModel>
}