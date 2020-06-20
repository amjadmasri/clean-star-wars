package com.amjad.starwars.data.remote

import com.amjad.starwars.data.models.CharacterDataModel
import com.amjad.starwars.data.models.CharacterSearchResponse
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class CharacterRemoteSourceImp @Inject constructor(private val apiService:ApiService) :CharacterRemoteSource{
    override fun searchCharacter(
        name: String
    ): Single<CharacterSearchResponse> {
       return apiService.searchCharacterByName(name)
    }

    override fun getCharacterDetails(id: String): Single<CharacterDataModel> {
       return apiService.getCharacterDetails(id)
    }

    override fun getMoreCharacters(url: String): Single<CharacterSearchResponse> {
        return apiService.getMoreCharacters(url)
    }
}