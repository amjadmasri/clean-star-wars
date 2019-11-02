package com.amjad.starwars.domain.repository

import com.amjad.starwars.data.models.CharacterDataModel
import com.amjad.starwars.data.models.CharacterSearchResponse
import io.reactivex.Single
import retrofit2.Response

interface CharacterRepository {

    fun searchCharacter(name :String,page:String): Single<Response<CharacterSearchResponse>>

    fun getCharacterDetails(id:String) :Single<Response<CharacterDataModel>>
}