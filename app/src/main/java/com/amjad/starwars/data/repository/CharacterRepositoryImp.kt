package com.amjad.starwars.data.repository


import com.amjad.starwars.data.mappers.CharacterMapper
import com.amjad.starwars.data.models.CharacterSearchResponse
import com.amjad.starwars.data.remote.CharacterRemoteSource
import com.amjad.starwars.domain.models.CharacterDomainModel
import com.amjad.starwars.domain.repository.CharacterRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class CharacterRepositoryImp @Inject constructor(
    private val characterRemoteSource: CharacterRemoteSource,
    private val characterMapper: CharacterMapper
) : CharacterRepository {
    override fun searchCharacter(
        name: String
    ): Single<CharacterSearchResponse> {
        return characterRemoteSource.searchCharacter(name)
    }

    override fun getCharacterDetails(id: String): Observable<CharacterDomainModel> {
        return characterRemoteSource.getCharacterDetails(id)
            .flatMapObservable {
                Observable.just(
                    characterMapper.mapFromEntity(
                        it
                    )
                )
            }

    }
}