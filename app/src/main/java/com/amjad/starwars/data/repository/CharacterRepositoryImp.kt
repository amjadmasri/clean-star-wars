package com.amjad.starwars.data.repository

import com.amjad.starwars.data.models.CharacterDataModel
import com.amjad.starwars.data.models.CharacterSearchResponse
import com.amjad.starwars.data.remote.CharacterRemoteSource
import com.amjad.starwars.domain.repository.CharacterRepository
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class CharacterRepositoryImp @Inject constructor(private val characterRemoteSource: CharacterRemoteSource):CharacterRepository {
    override fun searchCharacter(name: String, page: String) : Single<Response<CharacterSearchResponse>>{
       return characterRemoteSource.searchCharacter(name,page)
    }

    override fun getCharacterDetails(id: String) : Single<Response<CharacterDataModel>> {
      return characterRemoteSource.getCharacterDetails(id)
    }
}