package com.amjad.starwars.domain.repository

import androidx.lifecycle.LiveData
import com.amjad.starwars.data.models.CharacterSearchResponse
import com.amjad.starwars.domain.models.CharacterDomainModel
import com.amjad.starwars.common.Resource
import io.reactivex.Single
import retrofit2.Response

interface CharacterRepository {

    fun searchCharacter(name: String, page: String): Single<Response<CharacterSearchResponse>>

    fun getCharacterDetails(id: String): LiveData<Resource<CharacterDomainModel>>
}