package com.amjad.starwars.data.repository


import androidx.paging.PagedList
import com.amjad.starwars.data.mappers.CharacterMapper
import com.amjad.starwars.data.remote.CharacterRemoteSource
import com.amjad.starwars.domain.models.CharacterDomainModel
import com.amjad.starwars.domain.repository.CharacterRepository
import io.reactivex.Observable
import javax.inject.Inject

class CharacterRepositoryImp @Inject constructor(
    private val characterRemoteSource: CharacterRemoteSource,
    private val characterMapper: CharacterMapper
) : CharacterRepository {
    override fun searchCharacter(
        name: String
    ): Observable<PagedList<CharacterDomainModel>> {
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