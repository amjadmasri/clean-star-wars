package com.amjad.starwars.domain.useCase

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.amjad.starwars.domain.models.CharacterDomainModel
import com.amjad.starwars.domain.models.PagedListing
import com.amjad.starwars.data.repository.CharacterDataSourceFactory
import com.amjad.starwars.domain.extensions.toResult
import com.amjad.starwars.domain.models.Result
import com.amjad.starwars.domain.repository.CharacterRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class SearchCharacterUseCase @Inject constructor(private val characterRepository: CharacterRepository)  {


    fun execute(characterName:String): Observable<Result<PagedList<CharacterDomainModel>>> {

        return characterRepository.searchCharacter(characterName)
            .map { it.toResult() }
    }
}