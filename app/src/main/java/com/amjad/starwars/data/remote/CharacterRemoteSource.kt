package com.amjad.starwars.data.remote

import com.amjad.starwars.data.models.CharacterDataModel
import com.amjad.starwars.data.models.CharacterSearchResponse
import io.reactivex.Single
import retrofit2.Response

interface CharacterRemoteSource {

    fun searchCharacter(name :String):Single<CharacterSearchResponse>

    fun getCharacterDetails(id:String):Single<CharacterDataModel>

    fun getMoreCharacters(url:String):Single<CharacterSearchResponse>
}